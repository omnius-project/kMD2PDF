package me.chill.style.elements

open class HeaderOne(fontSize: Double = 16.0) : Element(fontSize) {
  override var fontSize = super.fontSize * 2
}