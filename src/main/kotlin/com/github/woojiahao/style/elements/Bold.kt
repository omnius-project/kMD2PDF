package com.github.woojiahao.style.elements

import com.github.woojiahao.style.FontFamily

class Bold(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("strong", fontSize, fontFamily) {
  override var fontWeight = FontWeight.BOLD
}