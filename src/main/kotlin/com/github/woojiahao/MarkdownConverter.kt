package com.github.woojiahao

import com.github.woojiahao.MarkdownConverter.ConversionTarget.HTML
import com.github.woojiahao.MarkdownConverter.ConversionTarget.PDF
import com.github.woojiahao.modifiers.MediaReplacedElementFactory
import com.github.woojiahao.modifiers.figure.FigureExtension
import com.github.woojiahao.modifiers.toc.TableOfContentsNodeVisitor
import com.github.woojiahao.modifiers.toc.TableOfContentsVisitor
import com.github.woojiahao.modifiers.toc.generateTableOfContents
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.utility.extensions.isFileType
import com.github.woojiahao.utility.getFontDirectories
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
import org.jsoup.nodes.Document
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream
import com.github.kittinunf.result.Result as KResult

class MarkdownConverter private constructor(
  markdownDocument: MarkdownDocument,
  private val documentStyle: Style,
  targetLocation: File,
  private val documentProperties: DocumentProperties,
  conversionTarget: ConversionTarget
) {

  enum class ConversionTarget(val requiredExtension: String?, val isFolder: Boolean) {
    PDF("pdf", false), HTML(null, true)
  }

  private val conversionHandler by lazy {
    val html = generateBody()
    val css = generateCss()
    when (conversionTarget) {
      PDF -> PdfConversionHandler(
        html,
        css,
        targetLocation,
        mapOf("documentProperties" to documentProperties)
      )
      HTML -> HtmlConversionHandler(html, css, targetLocation)
    }
  }

  private val extensions = listOf(
    TaskListExtension.create(),
    TablesExtension.create(),
    StrikethroughExtension.create(),
    TocExtension.create(),
    FigureExtension(markdownDocument.file)
  )

  private val options = MutableDataSet().apply { set(Parser.EXTENSIONS, extensions) }

  private val parser = Parser.builder(options).build()

  private val htmlRenderer = HtmlRenderer.builder(options).build()

  private val tableOfContentsNodeVisitor = TableOfContentsNodeVisitor(
    TableOfContentsVisitor(documentProperties.tableOfContentsSettings)
  )

  private val parsedDocument = parser.parse(markdownDocument.file.readText())

  private val parsedDocumentBody
    get() = htmlRenderer.render(parsedDocument)

  private val pagePropertiesManager = PagePropertiesManager(documentProperties, documentStyle)

  init {
    tableOfContentsNodeVisitor.visit(parsedDocument)
  }

  fun convert() = conversionHandler.convert()

  private fun generateCss(): String {
    val css = StringBuilder()
    css += wrap(documentStyle.getStyles())
    css += wrap(pagePropertiesManager.toCss())
    css += wrap(".table-of-contents") {
      attributes {
        "page-break-after" to "always"
      }
    }

    // Fig caption configuration
    with(documentProperties.figcaptionSettings) {
      val figcaptionNumberLabel = "figures"

      if (isVisible) {
        css += wrap("body") {
          attributes {
            "counter-reset" to figcaptionNumberLabel
          }
        }

        css += wrap("figure") {
          attributes {
            "counter-increment" to figcaptionNumberLabel
          }
        }

        css += wrap("figure figcaption:before") {
          attributes {
            "content" to "'$prepend ' counter($figcaptionNumberLabel) ' $divider '"
          }
        }

        css += wrap("figure figcaption:after") {
          attributes {
            "content" to " '$append'"
          }
        }
      }
    }

    return css.toString()
  }

  fun generateBody(): String {
    with(StringBuilder().appendHTML()) {
      val tocSettings = documentProperties.tableOfContentsSettings
      if (tocSettings.isVisible) {
        div("table-of-contents") {
          h1 { +"Table of contents" }
          unsafe {
            raw(
              generateTableOfContents(
                tableOfContentsNodeVisitor.visitor.getTableOfContents(),
                tocSettings
              )
            )
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
        unsafe { +wrap(parsedDocumentBody.trim()) }
      }

      return finalize().toString()
    }
  }

  private fun wrap(content: String) = "\n$content\n"

  private fun wrap(elementName: String, cssSelector: CssSelector.() -> Unit) =
    wrap(CssSelector(elementName).apply(cssSelector).toCss())

  private operator fun StringBuilder.plusAssign(content: String) {
    append(content)
  }

  open class Builder {
    private var document: MarkdownDocument? = null
    private var style = Style()
    private var targetLocation: String? = null
    private var documentProperties = DocumentProperties.Builder().build()
    private var conversionTarget = PDF

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

    fun conversionTarget(conversionTarget: ConversionTarget): Builder {
      this.conversionTarget = conversionTarget
      return this
    }

    fun build(): MarkdownConverter {
      val doc = document
      check(doc != null) { "Markdown document must be set using document()" }

      val targetFile = createTargetFile()

      return MarkdownConverter(
        doc,
        style,
        targetFile,
        documentProperties,
        conversionTarget
      )
    }

    private fun createTargetFile(): File {
      val targetFile = createTargetOutputFile(targetLocation, conversionTarget)
      with(conversionTarget) {
        if (isFolder) {
          check(targetFile.extension.isEmpty()) { "Target location should not have extension" }
        } else {
          requiredExtension ?: return@with
          check(targetFile.isFileType(requiredExtension)) {
            "Target location must have a .$requiredExtension extension"
          }
        }
      }

      return targetFile
    }

    private fun createTargetOutputFile(filePath: String?, conversionTarget: ConversionTarget) =
      filePath?.let { File(it) } ?: createFileRelativeToDocument(conversionTarget)

    private fun createFileRelativeToDocument(conversionTarget: ConversionTarget): File {
      with(document!!.file) {
        check(this.parentFile != null) { "File must have parent folder" }

        val fileName =
          if (!conversionTarget.isFolder) "$nameWithoutExtension.${conversionTarget.requiredExtension}"
          else nameWithoutExtension

        return File(this.parentFile, fileName)
      }
    }
  }
}