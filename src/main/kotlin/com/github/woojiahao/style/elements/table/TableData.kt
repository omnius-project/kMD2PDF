package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.utility.c
import java.awt.Color

class TableData(settings: Settings) : Element("td", settings) {
  init {
    val border by CssProperty<BorderBox?>(
      settings,
      BorderBox(Border(1.0.px, SOLID, Color.BLACK)),
      BorderBox(Border(1.0.px, SOLID, c("EE")))
    )
    this.border = border

    padding = Box(5.0.px)
  }
}