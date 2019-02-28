package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.FontFamily

open class DocumentArea(
  elementName: String,
  fontSize: Double,
  fontFamily: FontFamily
) : Element(elementName, fontSize, fontFamily) {

  open val left = DocumentText("left", fontSize, fontFamily.clone())
  open val right = DocumentText("right", fontSize, fontFamily.clone())
  open val center = DocumentText("center", fontSize, fontFamily.clone())

  fun left(style: DocumentText.() -> Unit) = left.style()
  fun right(style: DocumentText.() -> Unit) = right.style()
  fun center(style: DocumentText.() -> Unit) = center.style()
}