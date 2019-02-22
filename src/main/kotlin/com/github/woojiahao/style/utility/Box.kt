package com.github.woojiahao.style.utility

/**
 * Box of measurements for all directions.
 */
open class Box<T>(val top: T, val right: T, val bottom: T, val left: T) {
  constructor(vertical: T, horizontal: T) : this(vertical, horizontal, vertical, horizontal)
  constructor(all: T) : this(all, all, all, all)

  fun toCss() = "$top $right $bottom $left"

  fun <P> toCss(modification: (T) -> P) =
    "${modification(top)} ${modification(right)} ${modification(bottom)} ${modification(left)}"
}