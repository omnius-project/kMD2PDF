package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.utility.c
import java.awt.Color

class Link(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element(fontSize, fontFamily) {
  override var textDecoration = TextDecoration.UNDERLINE
  override var fontColor: Color? = c("448AFF")
}