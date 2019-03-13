package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.*
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.utility.c
import java.awt.Color

/**
 * <th></th> element.
 */
class TableHeader(settings: Settings) : Element("th", settings) {
  override var border by cssProperty<BorderBox?>(BorderBox(Border(1.0, SOLID, Color.BLACK)), settings.theme) {
    darkTheme = BorderBox(Border(1.0, SOLID, c("EEEEEE")))
  }
  override var fontWeight: FontWeight? = FontWeight.BOLD
  override var padding: Box<Measurement<Double>>? = Box(5.0.px)
}