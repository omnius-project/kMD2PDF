package com.github.woojiahao.style.elements.lists

class OrderedList : List("ol") {
  init {
    listStyleType = ListStyleType.DECIMAL
  }
}