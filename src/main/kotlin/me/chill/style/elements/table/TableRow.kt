package me.chill.style.elements.table

import me.chill.style.Border
import me.chill.style.BorderBox
import me.chill.style.FontFamily
import me.chill.style.elements.Element
import java.awt.Color

/**
 * <tr></tr> element.
 */
class TableRow(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element(fontSize, fontFamily)