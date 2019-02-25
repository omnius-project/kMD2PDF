package com.github.woojiahao.style.css

class CssSelector(
  private val selector: String,
  val attributes: CssAttributes = CssAttributes()
) {
  override fun toString() = "$selector {\n${attributes.toCss()}\n}"
}