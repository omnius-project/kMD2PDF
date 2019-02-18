package me.chill.style

class BorderBox(
  topBorder: Border,
  rightBorder: Border,
  bottomBorder: Border,
  leftBorder: Border
) : Box<Border>(
  topBorder.copy(),
  rightBorder.copy(),
  bottomBorder.copy(),
  leftBorder.copy()
) {
  constructor(vertical: Border, horizontal: Border) : this(vertical, horizontal, vertical, horizontal)
  constructor(all: Border) : this(all, all, all, all)

  fun top(style: Border.() -> Unit) = top.style()

  fun right(style: Border.() -> Unit) = right.style()

  fun bottom(style: Border.() -> Unit) = bottom.style()

  fun left(style: Border.() -> Unit) = left.style()

  fun all(style: Border.() -> Unit) {
    right(style)
    top(style)
    bottom(style)
    left(style)
  }
}