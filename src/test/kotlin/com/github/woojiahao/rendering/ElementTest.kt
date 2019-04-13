package com.github.woojiahao.rendering

import com.github.woojiahao.rendering.utility.assertMarkdown

open class ElementTest(private val folder: String) {
  fun assert(file: String) = assertMarkdown(folder, file)
}