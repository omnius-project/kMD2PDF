package me.chill.style.elements

import me.chill.style.Border
import me.chill.style.BorderBox
import me.chill.style.Box
import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF
import me.chill.utility.c

class BlockQuote(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element("blockquote", fontSize, fontFamily) {
  override var backgroundColor = c("BBDEFB")
  override var padding: Box<Double>? = Box(10.0, 20.0)
  override var margin: Box<Double>? = Box(0.0)
  override var border = BorderBox(
    Border(),
    Border(),
    Border(),
    Border(5.0, Border.BorderStyle.SOLID, c("1565C0"))
  )
}