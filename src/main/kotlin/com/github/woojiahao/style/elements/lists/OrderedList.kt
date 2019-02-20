package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.FontFamily

class OrderedList(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : List("ol", fontSize, fontFamily) {
  override var listStyleType = ListStyleType.DECIMAL
}