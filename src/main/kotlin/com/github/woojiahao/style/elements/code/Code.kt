package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.c

open class Code(elementName: String, settings: Settings) : Element(elementName, settings) {
  init {
    val fontFamily by CssProperty(settings, fallback = settings.monospaceFont)
    this.fontFamily = fontFamily

    val textColor by CssProperty(settings, c("073642"), c("eee8d5"))
    this.textColor = textColor

    val backgroundColor by CssProperty(settings, c("fdf6e3"), c("002b36"))
    this.backgroundColor = backgroundColor
  }
}