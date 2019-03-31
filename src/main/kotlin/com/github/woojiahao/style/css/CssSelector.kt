package com.github.woojiahao.style.css

class CssSelector(private val selector: String, val attributes: CssAttributes = CssAttributes()) {

  private val nestedCssSelectors by lazy { mutableListOf<CssSelector>() }

  fun attributes(attributes: CssAttributes.() -> Unit) = this.attributes.attributes()

  fun nested(nested: MutableList<CssSelector>.() -> Unit) = nestedCssSelectors.nested()

  fun toCss(): String =
    with(mutableListOf("$selector {")) {
      if (attributes.attrs.isNotEmpty())
        this += attributes.toCss()

      if (nestedCssSelectors.isNotEmpty())
        this += nestedCssSelectors.joinToString("\n") { it.toCss() }

      this += "}"
      joinToString("\n")
    }
}

inline fun cssSelector(selectorName: String, style: CssSelector.() -> Unit) =
  CssSelector(selectorName).apply(style)