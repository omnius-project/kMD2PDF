package me.chill

import me.chill.utility.getFontDirectories
import org.commonmark.node.Node
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

/**
 * Handles the rendering of a markdown file to PDF
 */
class MarkdownRenderer(
  private val parsedMarkdownDocument: Node,
  private val style: PDFStyle
) {

  private val htmlRenderer = HtmlRenderer
    .builder()
    .attributeProviderFactory { HTMLAttributeProvider(style) }
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
      val fontDirectories = getFontDirectories()
      if (fontDirectories.all { !File(it).exists() }) {
        println("Font folder could not be located on your system, fonts will not render properly")
      }
      // todo: create extension function for recursively adding font directory (linux requires it)
      fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
      layout()
      createPDF(FileOutputStream(targetFile))
    }
  }
}