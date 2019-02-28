package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.cssSelector

class CodeBlock(
  fontSize: Double,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.MONOSPACE)
) : Code("pre", fontSize, fontFamily) {

  override fun toCss(): String {
    css.add(cssSelector("pre > code") {
      attributes {
        "font-family" to fontFamily
      }
    })
    return super.toCss()
  }
}