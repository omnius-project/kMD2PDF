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

  // TODO: Generate CSS file on the go, link to said file, render PDF, then delete the file
  fun toHTML() = """
  <!DOCTYPE html>
  <html>
    <body>
      ${htmlRenderer.render(parsedMarkdownDocument).trim()}
    </body>
  </html>
  """.trimIndent()



  fun constructPDF(targetFile: File) {
//    println(toHTML())

    val renderer = ITextRenderer()
    renderer.setDocumentFromString(toHTML())
    renderer.layout()
    renderer.createPDF(FileOutputStream(targetFile))
  }
}