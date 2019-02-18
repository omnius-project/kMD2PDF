package me.chill.style.elements.table

import me.chill.style.Border
import me.chill.style.BorderBox
import me.chill.style.Box
import me.chill.style.FontFamily
import me.chill.style.elements.Element
import me.chill.utility.c
import java.awt.Color

/**
 * <th></th> element.
 */
class TableHeader(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element(fontSize, fontFamily) {
  override var border = BorderBox(
    Border(),
    Border(),
    Border(1.0, Border.BorderStyle.SOLID, c("E0E0E0")),
    Border()
  )
  override var fontWeight = FontWeight.BOLD
  override var padding: Box<Double>? = Box(5.0)
}