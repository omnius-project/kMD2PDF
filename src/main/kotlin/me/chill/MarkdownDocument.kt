package me.chill

import me.chill.rendering.MarkdownRenderer
import me.chill.style.PDFStyle
import me.chill.utility.extensions.isFileType
import org.commonmark.parser.Parser
import java.io.File
import com.itextpdf.text.Document as ITextDocument
import org.commonmark.node.Document as CommonMarkDocument

class MarkdownDocument(private val file: File, private val style: PDFStyle = PDFStyle()) {

  constructor(filePath: String, style: PDFStyle = PDFStyle()) : this(File(filePath), style)

  private var markdownRenderer: MarkdownRenderer

  init {
    with(file) {
      require(exists()) { "File path ($path) must exist" }
      require(isFile) { "File path ($path) must point to a file" }
      require(isFileType("md")) { "File ($nameWithoutExtension) must be a markdown document" }

      val parsedDocument = Parser.builder().build().parse(readText())
      markdownRenderer = MarkdownRenderer(parsedDocument, style)
    }
  }

  /**
   * Converts the current markdown document into a PDF.
   *
   * If no [filePath] is supplied, the exported PDF is exported to the same parent
   * folder of the markdown file.
   *
   * If a [filePath] is supplied, the exported PDF is exported to the file path
   * specified. The [filePath] supplied must end with a file with the extension of
   * `.pdf`
   */
  fun convertToPDF(filePath: String? = null) {
    with(createTargetOutputFile(filePath)) {
      require(isFileType("pdf")) { "File ($nameWithoutExtension) must be a PDF" }

      markdownRenderer.constructPDF(this)
    }
  }

  private fun createTargetOutputFile(filePath: String?) =
    filePath?.let { File(it) } ?: createFileRelativeToDocument()

  private fun createFileRelativeToDocument(): File {
    with(file.parentFile) {
      check(this != null) { "File cannot have no parent folder" }
      return File(this, "${file.nameWithoutExtension}.pdf")
    }
  }
}