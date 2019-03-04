package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.c

class BlockQuote(fontSize: Double, fontFamily: FontFamily) : Element("blockquote", fontSize, fontFamily) {
  override var backgroundColor = c("FFCDD2")
  override var padding: Box<Double>? = Box(10.0, 20.0)
  override var border: BorderBox? = BorderBox(
    Border(),
    Border(),
    Border(),
    Border(5.0, SOLID, c("F44336"))
  )
}