package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.utility.c
import java.awt.Color.BLACK

class Ruler(settings: Settings) : Element("hr", settings) {
  override var border by cssProperty<BorderBox?>(BorderBox(Border(1.0, SOLID, BLACK)), settings.theme) {
    darkTheme = BorderBox(Border(1.0, SOLID, c("EEEEEE")))
  }
}