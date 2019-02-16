package me.chill

import me.chill.rendering.MarkdownRenderer
import me.chill.style.PDFStyle
import me.chill.utility.extensions.isFileType
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import java.io.File
import com.itextpdf.text.Document as ITextDocument
import org.commonmark.node.Document as CommonMarkDocument

/**
 * Corresponds to an existing markdown [file] on the local machine, this file can be retrieved
 * either through a [File] object or the filePath of the target file.
 *
 * Optionally provide a [style] to specify the styling of the converted PDF.
 * Custom styles can be created by following this [guide.](https://woojiahao.github.io/kMD2PDF/#/)
 */
class MarkdownDocument(
  private val file: File,
  private val style: PDFStyle = PDFStyle()
) {

  constructor(filePath: String, style: PDFStyle = PDFStyle()) : this(File(filePath), style)

  private val markdownRenderer = MarkdownRenderer(this, style)

  var parsedDocument: Node
    private set

  init {
    with(file) {
      require(exists()) { "File path ($path) must exist" }
      require(isFile) { "File path ($path) must point to a file" }
      require(isFileType("md")) { "File ($name) must be a markdown document" }

      parsedDocument = Parser.builder().build().parse(readText())
    }
  }

  /**
   * Converts the current markdown document into a PDF.
   *
   * If no [filePath] is supplied, the converted PDF is exported to the same parent
   * folder of the markdown file.
   *
   * If a [filePath] is supplied, the converted PDF is exported to the file path
   * specified. The [filePath] supplied must end with a file with the extension of
   * `.pdf`
   */
  fun convertToPDF(filePath: String? = null) {
    with(createTargetOutputFile(filePath)) {
      require(isFileType("pdf")) { "File ($nameWithoutExtension) must be a PDF" }
      markdownRenderer.constructPDF(this)
    }
  }

  /**
   * Creates the output file for the converted PDF document.
   *
   * If no [filePath] is supplied, the converted PDF is exported to the same parent
   * folder of the markdown file.
   *
   * If a [filePath] is supplied, the converted PDF is exported to the file path
   * specified. The [filePath] supplied must end with a file with the extension of
   * `.pdf`
   */
  private fun createTargetOutputFile(filePath: String?) =
    filePath?.let { File(it) } ?: createFileRelativeToDocument()

  /**
   * Returns an output file relative to the markdown document location.
   */
  private fun createFileRelativeToDocument(): File {
    with(file.parentFile) {
      check(this != null) { "File cannot have no parent folder" }
      return File(this, "${file.nameWithoutExtension}.pdf")
    }
  }
}