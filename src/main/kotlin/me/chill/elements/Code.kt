package me.chill.elements

import java.awt.Color

open class Code : Element() {
  override val fontFamily = listOf("Courier")
  override val fontColor: Color = Color.WHITE
  override val backgroundColor: Color = Color.BLACK
}