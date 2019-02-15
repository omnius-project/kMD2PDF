package me.chill.style.elements

import java.awt.Color

open class Link(fontSize: Double = 16.0) : Element(fontSize) {
  override var textDecoration = TextDecoration.UNDERLINE
  override var fontColor: Color = Color.BLUE
}