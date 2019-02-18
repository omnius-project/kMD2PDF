package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF
import me.chill.utility.c

class BlockQuote(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element(fontSize, fontFamily) {
  override var backgroundColor = c("ECEFF1")
}