package com.github.woojiahao

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
  private val targetLocation: File,
  private val documentProperties: DocumentProperties
) {

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

  fun convert(): KResult<File, Exception> {
    with(ITextRenderer()) {
      val content = htmlToXML(generateHtml())
      val outputLocation by lazy { FileOutputStream(targetLocation) }

      println(content)

      sharedContext.replacedElementFactory = MediaReplacedElementFactory(
        documentProperties,
        sharedContext.replacedElementFactory
      )
      setDocumentFromString(content)
      loadFontDirectories()
      layout()
      return try {
        createPDF(outputLocation)
        KResult.success(targetLocation)
      } catch (e: Exception) {
        KResult.error(e)
      }
    }
  }

  private fun htmlToXML(html: String) =
    Jsoup
      .parse(html)
      .apply { outputSettings().syntax(Document.OutputSettings.Syntax.xml) }
      .let { it.html().replace("&nbsp;", " ") }

  private fun generateHtml() =
    StringBuilder()
      .appendHTML()
      .html {
        head {
          style {
            unsafe { raw(generateCss()) }
          }
        }
        body {
          unsafe { raw(generateBody()) }
        }
      }
      .toString()

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