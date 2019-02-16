package me.chill.style.elements

import me.chill.style.FontFamily

class HeaderOne(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("sans-serif")
) : Element(fontSize, fontFamily) {
  override var fontSize = super.fontSize * 2
}