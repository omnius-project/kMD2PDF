package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.utility.cssSelector

class CodeBlock(settings: Settings) : Code("pre", settings) {
  override var padding: Box<Measurement<Double>>? = Box(10.0.px)

  override fun toCss(): String {
    css.add(cssSelector("pre > code") { attributes { "font-family" to fontFamily } })
    return super.toCss()
  }
}