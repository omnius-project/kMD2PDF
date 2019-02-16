package me.chill.style.elements

import me.chill.style.FontFamily

open class HeaderFour(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("sans-serif")
) : Element(fontSize, fontFamily) {
  override var fontSize = super.fontSize
}