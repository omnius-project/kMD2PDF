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
    backgroundColor.value = c("EE")
    textColor.value = Color.BLACK
    padding.value = Box(10.0.px, 20.0.px)
    border = CssProperty(
      settings.theme,
      BorderBox(Border(), Border(), Border(), Border(5.0.px, SOLID, c("E0"))),
      BorderBox(Border(), Border(), Border(), Border(5.0.px, SOLID, c("FA")))
    )
  }

  override fun toCss(): String {
    css += cssSelector("blockquote > p") { attributes { "color" to textColor.value?.cssColor() } }
    return super.toCss()
  }
}