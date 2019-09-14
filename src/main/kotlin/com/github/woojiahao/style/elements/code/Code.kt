package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.c

open class Code(elementName: String, settings: Settings) : Element(elementName, settings) {
  init {
    fontFamily = CssProperty(settings.theme, fallback = settings.monospaceFont)
    textColor = CssProperty(settings.theme, c("073642"), c("eee8d5"))
    backgroundColor = CssProperty(settings.theme, c("fdf6e3"), c("002b36"))
  }
}