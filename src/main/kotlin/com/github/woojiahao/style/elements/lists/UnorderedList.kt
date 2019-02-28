package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.utility.FontFamily

class UnorderedList(
  fontSize: Double,
  fontFamily: FontFamily
) : List("ul", fontSize, fontFamily) {
  override var listStyleType = List.ListStyleType.CIRCLE
}