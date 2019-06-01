package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import java.awt.Color

class BlockQuote(settings: Settings) : Element("blockquote", settings) {
  init {
    backgroundColor = c("EEEEEE")
    textColor = Color.BLACK
    padding = Box(10.0.px, 20.0.px)
    val border by CssProperty<BorderBox?>(
      settings,
      BorderBox(Border(), Border(), Border(), Border(5.0.px, SOLID, c("E0E0E0")))
    )
    this.border = border
  }

  override fun toCss(): String {
    css += cssSelector("blockquote > p") { attributes { "color" to textColor?.cssColor() } }
    return super.toCss()
  }
}