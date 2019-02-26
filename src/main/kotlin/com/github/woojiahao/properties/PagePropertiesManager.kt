package com.github.woojiahao.properties

import com.github.woojiahao.utility.cssSelector

class PagePropertiesManager(documentProperties: DocumentProperties) {

  private val size = documentProperties.size
  private val margins = documentProperties.margins
  private val leftPageMargins = documentProperties.leftPageMargins
  private val rightPageMargins = documentProperties.rightPageMargins

  private val parentPageSelector = cssSelector("@page") {
    attributes {
      "size" to size.size
      "margin" to margins?.toCss { "${it}in" }
    }

    nested {
      this += cssSelector("@top-left") {
        attributes { "content" to "element(header-left)" }
      }

      this += cssSelector("@top-center") {
        attributes { "content" to "element(header-center)" }
      }

      this += cssSelector("@top-right") {
        attributes { "content" to "element(header-right)" }
      }
    }
  }

  private val leftPageSelector = cssSelector("@page :left") {
    attributes {
      "margin" to leftPageMargins?.toCss { "${it}in" }
    }
  }

  private val rightPageSelector = cssSelector("@page :right") {
    attributes {
      "margin" to rightPageMargins?.toCss { "${it}in" }
    }
  }

  fun toCss() = listOf(
    parentPageSelector,
    leftPageSelector,
    rightPageSelector
  ).joinToString("\n\n") { it.toCss() }
}