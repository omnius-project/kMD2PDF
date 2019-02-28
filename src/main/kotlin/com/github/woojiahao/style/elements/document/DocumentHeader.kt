package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.cssSelector

class DocumentHeader(
  fontSize: Double,
  fontFamily: FontFamily
) : DocumentArea("header", fontSize, fontFamily) {

  override val left = DocumentText(".header-left", fontSize, fontFamily.clone())
  override val right = DocumentText(".header-right", fontSize, fontFamily.clone())
  override val center = DocumentText(".header-center", fontSize, fontFamily.clone())

  override fun toCss(): String {
    val headerLeftCss = cssSelector(".header-left") {
      attributes {
        "position" to "running(header-left)"
        "text-align" to "left"
      }
    }

    val headerRightCss = cssSelector(".header-right") {
      attributes {
        "position" to "running(header-right)"
        "text-align" to "right"
      }
    }

    val headerCenterCss = cssSelector(".header-center") {
      attributes {
        "position" to "running(header-center)"
        "text-align" to "center"
      }
    }

    css.addAll(listOf(headerLeftCss, headerCenterCss, headerRightCss))

    return super.toCss()
  }
}