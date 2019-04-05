package com.github.woojiahao.style.utility

class BorderBox(top: Border, right: Border, bottom: Border, left: Border) : Box<Border>(
  top.copy(),
  right.copy(),
  bottom.copy(),
  left.copy()
) {
  constructor(vertical: Border, horizontal: Border) : this(vertical, horizontal, vertical, horizontal)
  constructor(all: Border) : this(all, all, all, all)
}