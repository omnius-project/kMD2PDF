package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.c

open class Code(elementName: String) : Element(elementName) {
  override var fontFamily by CssProperty(fallback = Settings.monospaceFont)
  override var textColor by CssProperty(c("073642"), c("eee8d5"))
  override var backgroundColor by CssProperty(c("fdf6e3"), c("002b36"))
}