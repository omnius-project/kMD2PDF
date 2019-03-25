package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.c

open class Code(elementName: String) : Element(elementName) {
  init {
    val fontFamily by CssProperty(fallback = Settings.monospaceFont)
    this.fontFamily = fontFamily

    val textColor by CssProperty(c("073642"), c("eee8d5"))
    this.textColor = textColor

    val backgroundColor by CssProperty(c("fdf6e3"), c("002b36"))
    this.backgroundColor = backgroundColor
  }
}