package com.github.woojiahao

import com.github.woojiahao.utility.extensions.isFileType
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import java.io.File
import org.commonmark.node.Document as CommonMarkDocument

/**
 * Corresponds to an existing markdown [file] on the local machine, this file can be retrieved
 * either through a [File] object or the filePath of the target file.
 *
 * Optionally provide a [style] to specify the styling of the converted PDF.
 * Custom styles can be created by following this [guide.](https://woojiahao.github.io/kMD2PDF/#/)
 */
class MarkdownDocument(val file: File) {

  constructor(filePath: String) : this(File(filePath))

  private val extensions = listOf(
    TablesExtension.create(),
    StrikethroughExtension.create()
  )
  private val parser = Parser
    .builder()
    .extensions(extensions)
    .build()

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
}