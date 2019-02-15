package me.chill.elements

import java.awt.Color

open class Link : Element() {
  override var textDecoration = TextDecoration.UNDERLINE
  override var fontColor: Color = Color.BLUE
}