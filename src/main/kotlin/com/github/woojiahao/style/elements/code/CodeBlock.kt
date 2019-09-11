package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.px

class CodeBlock(settings: Settings) : Code("pre", settings) {
  init {
    padding.value = Box(10.0.px)
  }

  override fun toCss(): String {
    css += cssSelector("pre > code") {
      attributes {
        "font-family" to fontFamily.value
      }
    }

    css += cssSelector("pre") {
      attributes {
        "white-space" to "pre-wrap"
      }
    }

    return super.toCss()
  }
}