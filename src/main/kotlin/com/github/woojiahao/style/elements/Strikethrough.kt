package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.FontFamily

class Strikethrough(
  fontSize: Double,
  fontFamily: FontFamily
) : Element("del", fontSize, fontFamily) {
  override var textDecoration = TextDecoration.LINE_THROUGH
}