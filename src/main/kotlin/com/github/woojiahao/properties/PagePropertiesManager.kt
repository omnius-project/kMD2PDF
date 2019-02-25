package com.github.woojiahao.properties

import com.github.woojiahao.style.css.CssAttributes
import com.github.woojiahao.style.css.CssSelector

class PagePropertiesManager(private val documentProperties: DocumentProperties) {

  private val size = documentProperties.size
  private val margins = documentProperties.margins
  private val leftPageMargins = documentProperties.leftPageMargins
  private val rightPageMargins = documentProperties.rightPageMargins

  private val parentPageSelector = CssSelector("@page")
  private val leftPageSelector = CssSelector("@page :left")
  private val rightPageSelector = CssSelector("@page :right")

  init {
    parentPageSelector.attributes.add("size", size.size)
    parentPageSelector.attributes.add("margin", margins?.toCss { "${it}in" })

    leftPageSelector.attributes.add("margin", leftPageMargins?.toCss { "${it}in" })

    rightPageSelector.attributes.add("margin", rightPageMargins?.toCss { "${it}in" })
  }

  fun toCss() = listOf(
    parentPageSelector,
    leftPageSelector,
    rightPageSelector
  ).joinToString("\n\n")
}