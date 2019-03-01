package com.github.woojiahao.style.css

class CssSelector(
  private val selector: String,
  val attributes: CssAttributes = CssAttributes()
) {

  private val nestedCssSelectors by lazy { mutableListOf<CssSelector>() }

  fun attributes(attributes: CssAttributes.() -> Unit) = this.attributes.attributes()

  fun nested(nested: MutableList<CssSelector>.() -> Unit) = nestedCssSelectors.nested()

  fun toCss(): String {
    val css = StringBuilder("$selector {\n").append(attributes.toCss())
    if (nestedCssSelectors.isNotEmpty()) {
      css.append("\n").append(nestedCssSelectors.joinToString("\n") { it.toCss() })
    }
    css.append("\n}")
    return css.toString()
  }
}