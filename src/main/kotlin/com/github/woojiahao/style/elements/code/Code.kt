package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.c

open class Code(elementName: String, settings: Settings) : Element(elementName, settings) {
  override var fontFamily = settings.monospaceFont
  override var textColor by cssProperty(c("073642"), settings.theme) {
    darkTheme = c("eee8d5")
  }
  override var backgroundColor by cssProperty(c("fdf6e3"), settings.theme) {
    darkTheme = c("002b36")
  }
}