package me.chill.style.elements.lists

import me.chill.style.FontFamily

class OrderedList(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : List(fontSize, fontFamily) {
  override var listStyleType = ListStyleType.DECIMAL
}