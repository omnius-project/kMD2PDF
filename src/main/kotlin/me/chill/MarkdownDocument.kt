package me.chill

import me.chill.utility.isFileType
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream
import com.itextpdf.text.Document as ITextDocument
import org.commonmark.node.Document as CommonMarkDocument

class MarkdownDocument(private val file: File, private val style: PDFStyle = PDFStyle()) {

  constructor(filePath: String, style: PDFStyle = PDFStyle()) : this(File(filePath), style)

  private var parsedMarkdownDocument: Node
  private val markdownParser = Parser.builder().build()
  private val htmlRenderer = HtmlRenderer
    .builder()
    .attributeProviderFactory { HTMLAttributeProvider(style) }
    .build()

  init {
    with(file) {
      require(exists()) { "File path ($path) must exist" }
      require(isFile) { "File path ($path) must point to a file" }
      require(isFileType("md")) { "File ($nameWithoutExtension) must be a markdown document" }

      parsedMarkdownDocument = markdownParser.parse(readText())
    }
  }

  // TODO: Generate CSS file on the go,  link to said file, render PDF, then delete the file
  fun toHTML() = """
    <!DOCTYPE html>
    <html>
      <body>
        ${htmlRenderer.render(parsedMarkdownDocument).trim()}
      </body>
    </html>
  """.trimIndent()

  fun convertToPDF(filePath: String? = null) {
    with(createTargetOutputFile(filePath)) {
      require(isFileType("pdf")) { "File ($nameWithoutExtension) must be a PDF" }

      constructPDF(this)
    }
  }

  private fun constructPDF(targetFile: File) {
    println(toHTML())

//    val renderer = ITextRenderer()
//    renderer.setDocumentFromString(toHTML())
//    renderer.layout()
//    renderer.createPDF(FileOutputStream(targetFile))
  }

  private fun createTargetOutputFile(filePath: String?) =
    filePath?.let { File(it) } ?: createFileRelativeToDocument()

  private fun createFileRelativeToDocument(): File {
    val parentFolder = file.parentFile
      ?: throw IllegalStateException("File cannot have no parent folder")
    return File(parentFolder, "${file.nameWithoutExtension}.pdf")
  }
}