package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.c

class BlockQuote(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("blockquote", fontSize, fontFamily) {
  override var backgroundColor = c("BBDEFB")
  override var padding: Box<Double>? =
    Box(10.0, 20.0)
  override var margin: Box<Double>? = Box(0.0)
  override var border = BorderBox(
    Border(),
    Border(),
    Border(),
    Border(
      5.0,
      Border.BorderStyle.SOLID,
      c("1565C0")
    )
  )
}