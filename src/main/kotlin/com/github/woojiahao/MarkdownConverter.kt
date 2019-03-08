package com.github.woojiahao

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.renderers.ImageNodeRenderer
import com.github.woojiahao.renderers.TaskListNodeRenderer
import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Style
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import com.github.woojiahao.utility.extensions.isFileType
import com.github.woojiahao.utility.getFontDirectories
import com.github.woojiahao.utility.px
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
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
  documentProperties: DocumentProperties
) {

  private val extensions = listOf(
    TablesExtension.create(),
    StrikethroughExtension.create()
  )

  private val parser = Parser
    .builder()
    .extensions(extensions)
    .build()

  private val htmlRenderer = HtmlRenderer
    .builder()
    .extensions(extensions)
    .nodeRendererFactory { ImageNodeRenderer(it) }
    .nodeRendererFactory { TaskListNodeRenderer(it) }
    .build()

  private val parsedDocument = parser.parse(markdownDocument.file.readText())

  private val pagePropertiesManager = PagePropertiesManager(documentProperties, documentStyle)

  fun convert(): KResult<File, Exception> {
    with(ITextRenderer()) {
      setDocumentFromString(generateHtml())
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

  fun generateHtml() =
    StringBuilder()
      .appendHTML()
      .html {
        head {
          style {
            unsafe {
              +wrapDocumentContent(documentStyle.getStyles())
              +wrapDocumentContent(pagePropertiesManager.toCss())
              +wrapDocumentContent(cssSelector(".task-list") {
                attributes {
                  "list-style-type" to "none"
                  "margin-left" to 0.px
                  "padding-left" to 0.px
                }
              }.toCss())
              +wrapDocumentContent(cssSelector(".task-list-item") {
                attributes {
                  "list-style-type" to "none"
                  "margin" to 0.px
                  "padding" to 0.px
                }
              }.toCss())
              +wrapDocumentContent(cssSelector(".task-list-item > input[type='checkbox']") {
                attributes {
                  "margin-right" to 10.px
                  if (documentStyle.settings.theme == Settings.Theme.DARK) {
                    "background-color" to Color.WHITE.cssColor()
                  }
                }
              }.toCss())
            }
          }
        }
        body {
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
            unsafe { +wrapDocumentContent(htmlRenderer.render(parsedDocument).trim()) }
          }
        }
      }.toString()

  private fun wrapDocumentContent(content: String) = "\n$content\n"

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