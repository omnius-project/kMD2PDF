package com.github.woojiahao.style.css

class CssStyle(
  private val selector: String,
  private val attributes: CssAttributes = CssAttributes()
) {
  override fun toString() = "$selector {\n${attributes.toCss()}\n}"
}