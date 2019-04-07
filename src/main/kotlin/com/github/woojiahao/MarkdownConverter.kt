package com.github.woojiahao

import com.github.kittinunf.result.failure
import com.github.woojiahao.modifiers.MediaReplacedElementFactory
import com.github.woojiahao.modifiers.renderers.ImageNodeRenderer
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.toc.TableOfContentsVisitor
import com.github.woojiahao.toc.generateTableOfContents
import com.github.woojiahao.utility.extensions.isFileType
import com.openhtmltopdf.DOMBuilder
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.toc.TocExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import com.github.kittinunf.result.Result as KResult

class MarkdownConverter private constructor(
  markdownDocument: MarkdownDocument,
  private val documentStyle: Style,
  private val targetLocation: File,
  private val documentProperties: DocumentProperties
) {

  private val extensions = listOf(
    TaskListExtension.create(),
    TablesExtension.create(),
    StrikethroughExtension.create(),
    TocExtension.create()
  )

  private val options = MutableDataSet().apply { set(Parser.EXTENSIONS, extensions) }

  private val parser = Parser.builder(options).build()

  private val htmlRenderer = HtmlRenderer
    .builder(options)
    .nodeRendererFactory { ImageNodeRenderer(markdownDocument.file, it) }
    .build()

  private val tableOfContentsVisitor = TableOfContentsVisitor(documentProperties.tableOfContentsSettings)

  private val parsedDocument = parser
    .parse(markdownDocument.file.readText())
    .apply { accept(tableOfContentsVisitor) }

  private val pagePropertiesManager = PagePropertiesManager(documentProperties, documentStyle)

  fun convert(): KResult<File, Exception> {
    val pdfRendererResult = buildPdfRenderer()

    val exception = pdfRendererResult.exception()
    exception?.let { return KResult.error(it) }

    val pdfRenderer = pdfRendererResult.get()

    val pdfBoxRenderer = pdfRenderer.buildPdfRenderer()
    with(pdfBoxRenderer) {
      sharedContext.replacedElementFactory = MediaReplacedElementFactory(
        documentProperties,
        sharedContext.replacedElementFactory
      )
      layout()
      return try {
        createPDF()
        KResult.success(targetLocation)
      } catch (e: Exception) {
        KResult.error(e)
      }
    }
  }

  private fun PdfRendererBuilder.parseW3cDocument(html: String) {
    val doc = Jsoup.parse(html)
    val dom = DOMBuilder.jsoup2DOM(doc)
    withW3cDocument(dom, null)
  }

  private fun buildPdfRenderer(): KResult<PdfRendererBuilder, Exception> {
    with(PdfRendererBuilder()) {
      val content = generateHtml()
      toStream(FileOutputStream(targetLocation))
      parseW3cDocument(content)

      return try {
        KResult.success(this)
      } catch (e: Exception) {
        KResult.error(e)
      }
    }
  }

  private fun <T : Any, E : Exception> KResult<T, E>.exception(): E? {
    var exception: E? = null

    failure { exception = it }

    return exception
  }

  private fun generateHtml() =
    StringBuilder()
      .appendHTML()
      .html {
        head {
          style {
            unsafe {
              +wrap(documentStyle.getStyles())
              +wrap(pagePropertiesManager.toCss())
              +wrap(".table-of-contents") {
                attributes {
                  "page-break-after" to "always"
                }
              }

              // Fig caption configuration
              with(documentProperties.figcaptionSettings) {
                val figcaptionNumberLabel = "figures"

                if (isVisible) {
                  +wrap("body") {
                    attributes {
                      "counter-reset" to figcaptionNumberLabel
                    }
                  }

                  +wrap("figure") {
                    attributes {
                      "counter-increment" to figcaptionNumberLabel
                    }
                  }

                  +wrap("figure figcaption:before") {
                    attributes {
                      "content" to "'$prepend ' counter($figcaptionNumberLabel) ' $divider '"
                    }
                  }

                  +wrap("figure figcaption:after") {
                    attributes {
                      "content" to " '$append'"
                    }
                  }
                }

              }
            }
          }
        }
        body {
          with(documentProperties.tableOfContentsSettings) {
            if (isVisible) {
              div("table-of-contents") {
                h1 { +"Table of contents" }
                unsafe {
                  raw(
                    generateTableOfContents(
                      tableOfContentsVisitor.getTableOfContents(),
                      this@with
                    )
                  )
                }
              }
            }
          }

          with(documentStyle.header) {
            div("header-left") { +left.getContents() }

            div("header-center") { +center.getContents() }

            div("header-right") { +right.getContents() }
          }

          with(documentStyle.footer) {
            div("footer-left") { +left.getContents() }

            div("footer-center") { +center.getContents() }

            div("footer-right") { +right.getContents() }
          }

          div("content") {
            unsafe { +wrap(htmlRenderer.render(parsedDocument).trim()) }
          }
        }
      }
      .toString()

  private fun wrap(content: String) = "\n$content\n"

  private fun wrap(elementName: String, cssSelector: CssSelector.() -> Unit) =
    wrap(CssSelector(elementName).apply(cssSelector).toCss())

  private fun ITextRenderer.loadFontDirectories() {
    val fontDirectories = getFontDirectories()
    val hasNoFontDirectory = fontDirectories.none { File(it).exists() }
    if (hasNoFontDirectory) {
      println("Font folders could not be located on your system, fonts will default")
    }
    fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
  }

  open class Builder {
    private var document: MarkdownDocument? = null
    private var style = Style()
    private var targetLocation: String? = null
    private var documentProperties = DocumentProperties.Builder().build()

    fun document(document: MarkdownDocument): Builder {
      this.document = document
      return this
    }

    fun style(style: Style): Builder {
      this.style = style
      return this
    }

    fun targetLocation(targetLocation: String): Builder {
      this.targetLocation = targetLocation
      return this
    }

    fun documentProperties(documentProperties: DocumentProperties): Builder {
      this.documentProperties = documentProperties
      return this
    }

    fun build(): MarkdownConverter {
      check(document != null) { "Markdown document must be set using document()" }

      val targetFile = createTargetOutputFile(targetLocation)
      check(targetFile.isFileType("pdf")) { "Target location must have a .pdf extension" }

      return MarkdownConverter(
        document!!,
        style,
        targetFile,
        documentProperties
      )
    }

    private fun createTargetOutputFile(filePath: String?) =
      filePath?.let { File(it) } ?: createFileRelativeToDocument()

    private fun createFileRelativeToDocument(): File {
      with(document!!.file) {
        check(this.parentFile != null) { "File cannot have no parent folder" }
        return File(this.parentFile, "$nameWithoutExtension.pdf")
      }
    }
  }
}