package me.chill.style.elements.headers

import me.chill.style.FontFamily
import me.chill.style.elements.Element

open class Header(
  headerName: String,
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF),
  headerScaleFactor: Double = 1.0
) : Element(headerName, fontSize, fontFamily) {
  override var fontWeight = FontWeight.BOLD
  override var fontSize = super.fontSize * headerScaleFactor
}