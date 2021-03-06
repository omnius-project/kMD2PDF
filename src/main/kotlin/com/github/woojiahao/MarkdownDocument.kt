package com.github.woojiahao

import com.github.woojiahao.utility.extensions.isFileType
import java.io.File

class MarkdownDocument(val file: File) {

  constructor(filePath: String) : this(File(filePath))

  init {
    with(file) {
      require(exists()) { "File path ($path) must exist" }
      require(isFile) { "File path ($path) must point to a file" }
      require(isFileType("md")) { "File ($name) must be a markdown document" }
      require(length() <= 2_147_483_648) { "Markdown document cannot exceed 2GB" }
    }
  }
}