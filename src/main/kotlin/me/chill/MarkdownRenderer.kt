package me.chill

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
    val renderer = ITextRenderer()
    renderer.setDocumentFromString(toHTML())
    renderer.fontResolver.addFontDirectory("C:\\WINDOWS\\FONTS\\", false)
    renderer.layout()
    renderer.createPDF(FileOutputStream(targetFile))
  }
}