package me.chill.rendering

import me.chill.style.GenericPDFStyle
import me.chill.style.PDFStyle
import me.chill.utility.getFontDirectories
import org.commonmark.node.Node
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

/**
 * Renders a markdown file to a PDF.
 */
// TODO: Pass a MarkdownDocument instead of a node
class MarkdownRenderer(
  private val parsedMarkdownDocument: Node,
  private val style: GenericPDFStyle
) {

  private val htmlRenderer = HtmlRenderer
    .builder()
    .attributeProviderFactory { HTMLStyleForPDFProvider(style) }
    .build()

  fun toHTML() = """
  <!DOCTYPE html>
  <html>
    <body>
      ${htmlRenderer.render(parsedMarkdownDocument).trim()}
    </body>
  </html>
  """.trimIndent()

  fun constructPDF(targetFile: File) {
    with(ITextRenderer()) {
      setDocumentFromString(toHTML())
      loadFontDirectories()
      layout()
      createPDF(FileOutputStream(targetFile))
    }
  }

  private fun ITextRenderer.loadFontDirectories() {
    val fontDirectories = getFontDirectories()
    val hasNoFontDirectory = fontDirectories.none { File(it).exists() }
    if (hasNoFontDirectory) {
      println("Font folders could not be located on your system, fonts will default")
    }
    fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
  }
}