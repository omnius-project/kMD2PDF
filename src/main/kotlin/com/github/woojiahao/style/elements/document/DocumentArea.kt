package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.cssSelector

open class DocumentArea(
  elementName: String,
  fontSize: Double,
  fontFamily: FontFamily
) : Element(elementName, fontSize, fontFamily) {

  private val leftSelector = "$elementName-left"
  private val rightSelector = "$elementName-right"
  private val centerSelector = "$elementName-center"

  val left = DocumentText(".$leftSelector", fontSize, fontFamily)
  val right = DocumentText(".$rightSelector", fontSize, fontFamily)
  val center = DocumentText(".$centerSelector", fontSize, fontFamily)

  fun left(style: DocumentText.() -> Unit) = left.style()
  fun right(style: DocumentText.() -> Unit) = right.style()
  fun center(style: DocumentText.() -> Unit) = center.style()

  override fun toCss(): String {
    val headerLeftCss = cssSelector(".$leftSelector") {
      attributes {
        "position" to "running($leftSelector)"
        "text-align" to "left"
      }
    }

    val headerRightCss = cssSelector(".$rightSelector") {
      attributes {
        "position" to "running($rightSelector)"
        "text-align" to "right"
      }
    }

    val headerCenterCss = cssSelector(".$centerSelector") {
      attributes {
        "position" to "running($centerSelector)"
        "text-align" to "center"
      }
    }

    css.addAll(listOf(headerLeftCss, headerCenterCss, headerRightCss))

    return super.toCss()
  }
}