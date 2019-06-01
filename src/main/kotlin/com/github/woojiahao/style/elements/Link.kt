package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.utility.c

class Link(settings: Settings) : Element("a", settings) {
  init {
    textColor = c("448AFF")
  }
}