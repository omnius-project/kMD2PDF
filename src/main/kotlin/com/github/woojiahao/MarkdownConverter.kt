package com.github.woojiahao

import com.github.woojiahao.modifiers.MediaReplacedElementFactory
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.modifiers.renderers.ImageNodeRenderer
import com.github.woojiahao.modifiers.renderers.TaskListNodeRenderer
import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.toc.TableOfContentsVisitor
import com.github.woojiahao.toc.generateTableOfContents
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.extensions.isFileType
import com.github.woojiahao.utility.getFontDirectories
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.BulletList
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.awt.Color
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
    TablesExtension.create(),
    StrikethroughExtension.create()
  )

  private val htmlRenderer = HtmlRenderer
    .builder()
    .extensions(extensions)
    .nodeRendererFactory { ImageNodeRenderer(markdownDocument.file, it) }
    .build()

  private val tableOfContentsVisitor = TableOfContentsVisitor(documentProperties.tableOfContentsSettings)

  private val parsedDocument = Parser
    .builder()
    .extensions(extensions)
    .build()
    .parse(markdownDocument.file.readText())
    .apply { accept(tableOfContentsVisitor) }

  private val pagePropertiesManager = PagePropertiesManager(documentProperties, documentStyle)

  fun convert(): KResult<File, Exception> {
    with(ITextRenderer()) {
      sharedContext.replacedElementFactory = MediaReplacedElementFactory(
        documentProperties,
        sharedContext.replacedElementFactory
      )
      val content = generateHtml()
      setDocumentFromString(content)
      loadFontDirectories()
      layout()
      return try {
        createPDF(FileOutputStream(targetLocation))
        KResult.success(targetLocation)
      } catch (e: Exception) {
        KResult.error(e)
      }
    }
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