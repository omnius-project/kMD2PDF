package com.github.woojiahao.style.elements

import com.github.woojiahao.style.FontFamily
import com.github.woojiahao.utility.c
import java.awt.Color

class Link(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("a", fontSize, fontFamily) {
  override var textDecoration = TextDecoration.UNDERLINE
  override var fontColor: Color? = c("448AFF")
}