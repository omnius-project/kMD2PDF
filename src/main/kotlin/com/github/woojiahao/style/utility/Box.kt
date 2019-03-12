package com.github.woojiahao.style.utility

open class Box<T>(val top: T, val right: T, val bottom: T, val left: T) {

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
}