package com.github.woojiahao.style.utility

open class Box<T>(var top: T, var right: T, var bottom: T, var left: T) {

  constructor(vertical: T, horizontal: T) : this(vertical, horizontal, vertical, horizontal)
  constructor(all: T) : this(all, all, all, all)

  fun toCss() = toCss(top, right, bottom, left)

  fun <P> toCss(modification: (T) -> P): String {
    val modifiedTop = modification(top)
    val modifiedRight = modification(right)
    val modifiedBottom = modification(bottom)
    val modifiedLeft = modification(left)

    return toCss(modifiedTop, modifiedRight, modifiedBottom, modifiedLeft)
  }

  fun <P> toCss(vararg items: P) = items.joinToString(" ")
  
  fun top(top: T) {
    this.top = top
  }

  fun right(right: T) {
    this.right = right
  }

  fun bottom(bottom: T) {
    this.bottom = bottom
  }

  fun left(left: T) {
    this.left = left
  }

  fun all(all: T) {
    right(all)
    top(all)
    bottom(all)
    left(all)
  }
}