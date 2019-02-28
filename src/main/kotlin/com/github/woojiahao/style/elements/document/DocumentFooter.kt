package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.cssSelector

class DocumentFooter(
  fontSize: Double,
  fontFamily: FontFamily
) : DocumentArea("footer", fontSize, fontFamily) {


  override val left = DocumentText(".footer-left", fontSize, fontFamily.clone())
  override val right = DocumentText(".footer-right", fontSize, fontFamily.clone())
  override val center = DocumentText(".footer-center", fontSize, fontFamily.clone())

  override fun toCss(): String {
    val footerLeftCss = cssSelector(".footer-left") {
      attributes {
        "position" to "running(footer-left)"
        "text-align" to "left"
      }
    }

    val footerRightCss = cssSelector(".footer-right") {
      attributes {
        "position" to "running(footer-right)"
        "text-align" to "right"
      }
    }

    val footerCenterCss = cssSelector(".footer-center") {
      attributes {
        "position" to "running(footer-center)"
        "text-align" to "center"
      }
    }

    css.addAll(listOf(footerLeftCss, footerCenterCss, footerRightCss))

    return super.toCss()
  }
}