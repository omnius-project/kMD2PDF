package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.c

open class Code(
  elementName: String,
  fontSize: Double,
  fontFamily: FontFamily
) : Element(elementName, fontSize, fontFamily) {
  override var textColor = c("FF3D00")
  override var backgroundColor = c("#F5F5F5")
  override var padding: Box<Double>? = Box(10.0)
}