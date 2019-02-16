package me.chill.style.elements

import me.chill.style.FontFamily
import java.awt.Color

open class Code(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("monospace")
) : Element(fontSize, fontFamily) {
  override var fontColor: Color = Color.decode("#FF3D00")
  override var backgroundColor: Color? = Color.decode("#FAFAFA")
}