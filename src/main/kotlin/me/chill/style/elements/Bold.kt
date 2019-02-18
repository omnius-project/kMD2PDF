package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF

class Bold(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element(fontSize, fontFamily) {
  override var fontWeight = FontWeight.BOLD
}