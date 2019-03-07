package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.Settings

class UnorderedList(settings: Settings) : List("ul", settings) {
  override var listStyleType = List.ListStyleType.CIRCLE
}