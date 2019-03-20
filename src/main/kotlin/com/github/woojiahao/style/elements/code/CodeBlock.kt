package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.px

class CodeBlock : Code("pre") {
  override var padding: Box<Measurement<Double>>? = Box(10.0.px)

  override fun toCss(): String {
    css.add(cssSelector("pre > code") { attributes { "font-family" to fontFamily } })
    return super.toCss()
  }
}