package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import java.awt.Color

class BlockQuote(settings: Settings) : Element("blockquote", settings) {
  override var backgroundColor = c("FAFAFA")
  override var textColor by cssProperty<Color?>(Color.BLACK, settings.theme)
  override var padding: Box<Double>? = Box(10.0, 20.0)
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