package com.github.woojiahao.style.elements

import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.utility.*
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import java.awt.Color

class BlockQuote : Element("blockquote") {
  override var backgroundColor = c("EEEEEE")
  override var textColor by cssProperty<Color?>(Color.BLACK, settings.theme)
  override var padding: Box<Measurement<Double>>? = Box(10.0.px, 20.0.px)
  override var border by cssProperty<BorderBox?>(BorderBox(
    Border(),
    Border(),
    Border(),
    Border(5.0, SOLID, c("E0E0E0"))
  ), settings.theme)

  override fun toCss(): String {
    css += cssSelector("blockquote > p") { attributes { "color" to textColor?.cssColor() } }
    return super.toCss()
  }
}