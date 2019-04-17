package com.github.woojiahao.generators

import com.github.woojiahao.style.css.CssSelector

abstract class AbstractContentGenerator {
  abstract fun generate(): String

  protected operator fun StringBuilder.plusAssign(content: String) {
    append(content)
  }

  protected fun wrap(content: String) = "\n$content\n"

  protected fun wrap(elementName: String, cssSelector: CssSelector.() -> Unit) =
    wrap(CssSelector(elementName).apply(cssSelector).toCss())
}