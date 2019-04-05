package com.github.woojiahao.style.elements

import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.utility.c
import java.awt.Color.BLACK

class Ruler : Element("hr") {
  init {
    val border by CssProperty<BorderBox?>(
      BorderBox(Border(1.0.px, SOLID, BLACK)),
      BorderBox(Border(1.0.px, SOLID, c("EE")))
    )
    this.border = border
  }
}