package com.github.woojiahao.properties

import com.github.woojiahao.style.css.CssSelector

class PagePropertiesManager(private val documentProperties: DocumentProperties) {

  private val size = documentProperties.size
  private val margins = documentProperties.margins
  private val leftPageMargins = documentProperties.leftPageMargins
  private val rightPageMargins = documentProperties.rightPageMargins

  private val parentPageSelector = CssSelector("@page")

  init {
    parentPageSelector.attributes.add("size", size.size)
    parentPageSelector.attributes.add("margin", margins.toCss { "${it}in" })
  }

  fun toCss() = parentPageSelector.toString()
}