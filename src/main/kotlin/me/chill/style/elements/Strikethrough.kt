package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF

class Strikethrough(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element("del", fontSize, fontFamily) {
  override var textDecoration = TextDecoration.LINE_THROUGH
}