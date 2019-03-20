package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.utility.c
import java.awt.Color

class TableHeader : Element("th") {
  init {
    val border by CssProperty<BorderBox?>(
      BorderBox(Border(1.0, SOLID, Color.BLACK)),
      BorderBox(Border(1.0, SOLID, c("EEEEEE")))
    )
    this.border = border

    fontWeight = FontWeight.BOLD
    padding = Box(5.0.px)
  }
}