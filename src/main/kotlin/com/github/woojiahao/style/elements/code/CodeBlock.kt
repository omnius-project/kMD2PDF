package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.utility.cssSelector

class CodeBlock(settings: Settings) : Code("pre", settings) {
  override fun toCss(): String {
    css.add(cssSelector("pre > code") { attributes { "font-family" to fontFamily } })
    return super.toCss()
  }
}