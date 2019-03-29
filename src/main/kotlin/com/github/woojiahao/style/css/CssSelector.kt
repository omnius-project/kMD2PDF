package com.github.woojiahao.style.css

class CssSelector(private val selector: String, val attributes: CssAttributes = CssAttributes()) {

  private val nestedCssSelectors by lazy { mutableListOf<CssSelector>() }

  fun attributes(attributes: CssAttributes.() -> Unit) = this.attributes.attributes()

  fun nested(nested: MutableList<CssSelector>.() -> Unit) = nestedCssSelectors.nested()

  fun toCss(): String {
    val cssContents = mutableListOf(
      "$selector {",
      attributes.toCss()
    )
    if (nestedCssSelectors.isNotEmpty()) {
      cssContents += nestedCssSelectors.joinToString("\n") { it.toCss() }
    }
    cssContents += "}"
    return cssContents.joinToString("\n")
  }
}

inline fun cssSelector(selectorName: String, style: CssSelector.() -> Unit) =
  CssSelector(selectorName).apply { style() }