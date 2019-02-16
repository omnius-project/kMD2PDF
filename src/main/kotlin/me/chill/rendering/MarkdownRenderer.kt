package me.chill.rendering

import kotlinx.html.body
import kotlinx.html.html
import kotlinx.html.stream.appendHTML
import kotlinx.html.unsafe
import me.chill.MarkdownDocument
import me.chill.style.GenericPDFStyle
import me.chill.utility.getFontDirectories
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

/**
 * Converts a [markdownDocument] to a PDF, applying the supplied [style] to the PDF.
 */
class MarkdownRenderer(
  private val markdownDocument: MarkdownDocument,
  private val style: GenericPDFStyle
) {

  private val htmlRenderer = HtmlRenderer
    .builder()
    .attributeProviderFactory { PDFStyleProvider(style) }
    .build()

  /**
   * Converts the [markdownDocument] into HTML for the PDF to render.
   * [style] is rendered along with the HTML as inline styles.
   */
  fun toHTML() = StringBuilder().appendHTML().html {
    body {
      unsafe {
        +htmlRenderer.render(markdownDocument.parsedDocument).trim()
      }
    }
  }.toString()

  /**
   * Converts the given [markdownDocument] to a PDF, saving it at the location
   * specified by [targetFile].
   */
  fun constructPDF(targetFile: File) {
    with(ITextRenderer()) {
      setDocumentFromString(toHTML())
      loadFontDirectories()
      layout()
      createPDF(FileOutputStream(targetFile))
    }
  }

  /**
   * Loads all available font directories into an [ITextRenderer] to be used with
   * the styles.
   *
   * If no font directories are located, a warning is made to the user, selected
   * fonts will not render.
   */
  private fun ITextRenderer.loadFontDirectories() {
    val fontDirectories = getFontDirectories()
    val hasNoFontDirectory = fontDirectories.none { File(it).exists() }
    if (hasNoFontDirectory) {
      println("Font folders could not be located on your system, fonts will default")
    }
    fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
  }
}