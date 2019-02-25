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

class MarkdownConverter private constructor(
  private val markdownDocument: MarkdownDocument,
  private val style: Style,
  private val onComplete: (File) -> Unit,
  private val onError: (Exception) -> Unit,
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

  fun convert() {
    with(ITextRenderer()) {
      println(generateMarkdownHTML())
      setDocumentFromString(generateMarkdownHTML())
      loadFontDirectories()
      layout()
      try {
        createPDF(FileOutputStream(targetLocation))
        onComplete(targetLocation)
      } catch (e: Exception) {
        onError(e)
      }
    }
  }

  fun generateMarkdownHTML() =
    StringBuilder()
      .appendHTML()
      .html {
        head {
          style {
            unsafe { +"\n${this@MarkdownConverter.style.getStyles()}\n" }
          }
        }
        body {
          unsafe { +"\n${htmlRenderer.render(markdownDocument.parsedDocument).trim()}\n" }
        }
      }.toString()

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
    private var onComplete: (File) -> Unit = { }
    private var onError: (Exception) -> Unit = { }
    private var targetLocation: String? = null

    fun markdownDocument(markdownDocument: MarkdownDocument): Builder {
      this.markdownDocument = markdownDocument
      return this
    }

    fun style(style: Style): Builder {
      this.style = style
      return this
    }

    fun onComplete(onComplete: (File) -> Unit): Builder {
      this.onComplete = onComplete
      return this
    }

    fun onError(onError: (Exception) -> Unit): Builder {
      this.onError = onError
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
        onComplete,
        onError,
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