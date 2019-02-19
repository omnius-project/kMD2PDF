package me.chill.style.elements

import me.chill.style.Border
import me.chill.style.Border.BorderStyle.SOLID
import me.chill.style.BorderBox
import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF
import java.awt.Color.BLACK

class Ruler(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element("hr", fontSize, fontFamily) {
  override var border = BorderBox(Border(1.0, SOLID, BLACK))
}