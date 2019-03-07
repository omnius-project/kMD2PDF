package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.Settings

class OrderedList(settings: Settings) : List("ol", settings) {
  override var listStyleType = ListStyleType.DECIMAL
}