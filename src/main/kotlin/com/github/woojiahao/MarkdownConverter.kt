package com.github.woojiahao

import com.github.woojiahao.style.Style
import com.github.woojiahao.utility.extensions.isFileType
import com.github.woojiahao.utility.getFontDirectories
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream
import com.github.kittinunf.result.Result as KResult

class MarkdownConverter private constructor(
  private val markdownDocument: MarkdownDocument,
  private val documentStyle: Style,
  private val targetLocation: File
) {

  private val extensions = listOf(
    TablesExtension.create(),
    StrikethroughExtension.create()
  )
  private val htmlRenderer = HtmlRenderer
    .builder()
    .extensions(extensions)
    .build()

  fun convert(): KResult<File, Exception> {
    with(ITextRenderer()) {
      println(generateHtml())

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
            unsafe { +wrapHtmlContent(documentStyle.getStyles()) }
          }
        }
        body {
          unsafe { +wrapHtmlContent(htmlRenderer.render(markdownDocument.parsedDocument).trim()) }
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

    fun build(): MarkdownConverter {
      check(markdownDocument != null) { "Markdown document must be set using markdownDocument()" }

      val targetFile = createTargetOutputFile(targetLocation)
      check(targetFile.isFileType("pdf")) { "Target location must have a .pdf extension" }

      return MarkdownConverter(
        markdownDocument!!,
        style,
        targetFile
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