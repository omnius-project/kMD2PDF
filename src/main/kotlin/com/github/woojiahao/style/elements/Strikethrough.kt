package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.FontFamily

class Strikethrough(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("del", fontSize, fontFamily) {
  override var textDecoration = TextDecoration.LINE_THROUGH
}