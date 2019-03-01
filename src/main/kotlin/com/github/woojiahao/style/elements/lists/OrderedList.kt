package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.utility.FontFamily

class OrderedList(fontSize: Double, fontFamily: FontFamily) : List("ol", fontSize, fontFamily) {
  override var listStyleType = ListStyleType.DECIMAL
}