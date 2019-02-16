package me.chill.style.elements

import me.chill.style.FontFamily

open class Bold(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("serif")
) : Element(fontSize, fontFamily) {
  override var fontWeight = FontWeight.BOLD
}