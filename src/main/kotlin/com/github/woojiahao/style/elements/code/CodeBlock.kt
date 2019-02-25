package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.css.CssAttributes
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.utility.FontFamily

class CodeBlock(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.MONOSPACE)
) : Code("pre", fontSize, fontFamily) {

  override fun toCss(): String {
    val codeBlockAttributes = CssAttributes()
      .add("font-family", fontFamily)
    val codeBlockSelector = CssSelector("pre > code", codeBlockAttributes)
    css.add(codeBlockSelector)
    return super.toCss()
  }
}