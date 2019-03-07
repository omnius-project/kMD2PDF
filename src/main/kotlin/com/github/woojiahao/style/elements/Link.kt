package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.utility.c
import java.awt.Color

class Link(settings: Settings) : Element("a", settings) {
  override var textColor: Color? = c("448AFF")
}