package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.Settings

class UnorderedList(settings: Settings) : List("ul", settings) {
  init {
    listStyleType = List.ListStyleType.CIRCLE
  }
}