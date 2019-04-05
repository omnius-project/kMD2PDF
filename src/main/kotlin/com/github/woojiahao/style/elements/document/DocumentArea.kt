package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.elements.Element

open class DocumentArea(elementName: String) : Element(elementName) {

  private val leftSelector = "$elementName-left"
  private val rightSelector = "$elementName-right"
  private val centerSelector = "$elementName-center"

  val left = DocumentText(".$leftSelector")
  val right = DocumentText(".$rightSelector")
  val center = DocumentText(".$centerSelector")

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