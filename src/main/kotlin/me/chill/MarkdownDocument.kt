package me.chill

import me.chill.rendering.MarkdownRenderer
import me.chill.style.AbstractStyle
import me.chill.style.Style
import me.chill.utility.extensions.isFileType
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
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
  private val style: AbstractStyle = Style()
) {

  constructor(filePath: String, style: AbstractStyle = Style()) : this(File(filePath), style)

  private val markdownRenderer = MarkdownRenderer(this, style)
  private val extensions = listOf(
    TablesExtension.create(),
    StrikethroughExtension.create()
  )
  private val parser = Parser
    .builder()
    .extensions(extensions)
    .build()
  private var onComplete: ((File) -> Unit)? = null
  private var onError: ((Exception) -> Unit)? = null

  var parsedDocument: Node
    private set

  init {
    with(file) {
      require(exists()) { "File path ($path) must exist" }
      require(isFile) { "File path ($path) must point to a file" }
      require(isFileType("md")) { "File ($name) must be a markdown document" }

      parsedDocument = parser.parse(readText())
    }
  }

  /**
   * Sets the action to perform when conversion is complete.
   * This action will pass the converted file as an input parameter.
   */
  fun onComplete(onComplete: (File) -> Unit): MarkdownDocument {
    this.onComplete = onComplete
    return this
  }

  /**
   * Sets the action to perform when conversion encounters an exception.
   * This action will pass the exception as an input parameter.
   */
  fun onError(onError: (Exception) -> Unit): MarkdownDocument {
    this.onError = onError
    return this
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
  fun toPDF(filePath: String? = null) {
    with(createTargetOutputFile(filePath)) {
      require(isFileType("pdf")) { "File ($nameWithoutExtension) must be a PDF" }
      markdownRenderer.constructPDF(this, onComplete, onError)
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