package com.github.woojiahao.markdown_converter

import com.github.woojiahao.markdown_converter.utility.assertMarkdown

open class ElementTest(private val folder: String) {
  fun assert(file: String) = assertMarkdown(folder, file)
}