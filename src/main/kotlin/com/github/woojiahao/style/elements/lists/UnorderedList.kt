package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.FontFamily

class UnorderedList(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : List("ul", fontSize, fontFamily) {
  override var listStyleType = List.ListStyleType.CIRCLE
}