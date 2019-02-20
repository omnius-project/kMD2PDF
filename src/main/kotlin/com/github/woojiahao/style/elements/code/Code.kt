package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Box
import com.github.woojiahao.style.FontFamily
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.c

open class Code(
  elementName: String,
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.MONOSPACE)
) : Element(elementName, fontSize, fontFamily) {
  override var fontColor = c("FF3D00")
  override var backgroundColor = c("#F5F5F5")
  override var padding: Box<Double>? = Box(10.0)
}