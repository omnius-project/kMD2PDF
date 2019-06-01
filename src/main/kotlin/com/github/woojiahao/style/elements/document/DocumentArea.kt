package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.elements.Element

open class DocumentArea(elementName: String, settings: Settings) : Element(elementName, settings) {

  private val leftSelector = "$elementName-left"
  private val rightSelector = "$elementName-right"
  private val centerSelector = "$elementName-center"

  val left = DocumentText(".$leftSelector", settings)
  val right = DocumentText(".$rightSelector", settings)
  val center = DocumentText(".$centerSelector", settings)

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