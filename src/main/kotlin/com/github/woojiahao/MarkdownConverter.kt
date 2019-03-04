package com.github.woojiahao

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.style.Style
import com.github.woojiahao.utility.cssSelector
import com.github.woojiahao.utility.extensions.isFileType
import com.github.woojiahao.utility.getFontDirectories
import com.github.woojiahao.utility.px
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream
import javax.management.Query.div
import com.github.kittinunf.result.Result as KResult

class MarkdownConverter private constructor(
  private val markdownDocument: MarkdownDocument,
  private val documentStyle: Style,
  private val targetLocation: File,
  documentProperties: DocumentProperties
) {

  private val extensions = listOf(
    TablesExtension.create(),
    StrikethroughExtension.create()
  )
  private val htmlRenderer = HtmlRenderer
    .builder()
    .extensions(extensions)
    .nodeRendererFactory { ImageNodeRenderer(it) }
    .nodeRendererFactory { TaskListNodeRenderer(it) }
    .build()

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
              +wrapHtmlContent(documentStyle.getStyles())
              +wrapHtmlContent(pagePropertiesManager.toCss())
              +cssSelector(".task-list") {
                attributes {
                  "list-style-type" to "none"
                  "margin-left" to 0.px
                  "padding-left" to 0.px
                }
              }.toCss()
              +cssSelector(".task-list-item") {
                attributes {
                  "list-style-type" to "none"
                  "margin" to 0.px
                  "padding" to 0.px
                }
              }.toCss()
              +cssSelector(".task-list-item > input[type='checkbox']") {
                attributes {
                  "margin-right" to 10.px
                }
              }.toCss()
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
            unsafe { +wrapHtmlContent(htmlRenderer.render(markdownDocument.parsedDocument).trim()) }
          }
        }
      }.toString()

  private fun wrapHtmlContent(content: String) = "\n$content\n"

  private fun ITextRenderer.loadFontDirectories() {
    val fontDirectories = getFontDirectories()
    val hasNoFontDirectory = fontDirectories.none { File(it).exists() }
    if (hasNoFontDirectory) {
      println("Font folders could not be located on your system, fonts will default")
    }
    fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
  }

  open class Builder {
    private var markdownDocument: MarkdownDocument? = null
    private var style = Style()
    private var targetLocation: String? = null
    private var documentProperties = DocumentProperties.Builder().build()

    fun markdownDocument(markdownDocument: MarkdownDocument): Builder {
      this.markdownDocument = markdownDocument
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
      check(markdownDocument != null) { "Markdown document must be set using markdownDocument()" }

      val targetFile = createTargetOutputFile(targetLocation)
      check(targetFile.isFileType("pdf")) { "Target location must have a .pdf extension" }

      return MarkdownConverter(
        markdownDocument!!,
        style,
        targetFile,
        documentProperties
      )
    }

    private fun createTargetOutputFile(filePath: String?) =
      filePath?.let { File(it) } ?: createFileRelativeToDocument()

    private fun createFileRelativeToDocument(): File {
      with(markdownDocument!!.file) {
        check(this.parentFile != null) { "File cannot have no parent folder" }
        return File(this.parentFile, "$nameWithoutExtension.pdf")
      }
    }
  }
}