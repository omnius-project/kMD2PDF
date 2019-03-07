package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.utility.c
import java.awt.Color

/**
 * <td></td> element.
 */
class TableData(settings: Settings) : Element("td", settings) {
  override var border by cssProperty<BorderBox?>(BorderBox(Border(1.0, SOLID, Color.BLACK)), settings.theme) {
    darkThemeProperty = BorderBox(Border(1.0, SOLID, c("FAFAFA")))
  }
  override var padding: Box<Double>? = Box(5.0)
}