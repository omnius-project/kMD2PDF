package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.SANS_SERIF

class DocumentText(
  elementName: String,
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element(elementName, fontSize, fontFamily) {

  private val contents = mutableListOf<String>()

  operator fun String.unaryPlus() = contents.add(this)

  fun getContents() = contents.joinToString("\n")
}