package com.github.woojiahao

import com.github.woojiahao.utility.extensions.isFileType
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import java.io.File
import org.commonmark.node.Document as CommonMarkDocument

class MarkdownDocument(val file: File) {

  constructor(filePath: String) : this(File(filePath))

  init {
    with(file) {
      require(exists()) { "File path ($path) must exist" }
      require(isFile) { "File path ($path) must point to a file" }
      require(isFileType("md")) { "File ($name) must be a markdown document" }
    }
  }
}