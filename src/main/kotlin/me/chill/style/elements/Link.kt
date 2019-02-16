package me.chill.style.elements

import me.chill.style.FontFamily
import java.awt.Color

open class Link(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("serif")
) : Element(fontSize, fontFamily) {
  override var textDecoration = TextDecoration.UNDERLINE
  override var fontColor: Color = Color.BLUE
}