package me.chill.elements

import java.awt.Color

open class Code : Element() {
  override var fontFamily = listOf("Courier")
  override var fontColor: Color = Color.WHITE
  override var backgroundColor: Color? = Color.BLACK
}