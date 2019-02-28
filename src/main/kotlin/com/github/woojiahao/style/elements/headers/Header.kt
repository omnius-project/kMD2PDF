package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.FontFamily

open class Header(
  headerName: String,
  fontSize: Double,
  fontFamily: FontFamily,
  headerScaleFactor: Double = 1.0
) : Element(headerName, fontSize, fontFamily) {
  override var fontWeight = FontWeight.BOLD
  override var fontSize = super.fontSize * headerScaleFactor
}