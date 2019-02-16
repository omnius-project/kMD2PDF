package me.chill.style.elements

import me.chill.style.FontFamily

open class HeaderSix(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("serif")
) : Element(fontSize, fontFamily) {
  override var fontSize = super.fontSize * 0.67
}