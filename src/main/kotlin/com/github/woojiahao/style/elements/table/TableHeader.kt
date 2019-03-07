package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import java.awt.Color

/**
 * <th></th> element.
 */
class TableHeader(settings: Settings) : Element("th", settings) {
  override var border: BorderBox? = BorderBox(Border(1.0, SOLID, Color.BLACK))
  override var fontWeight: FontWeight? = FontWeight.BOLD
  override var padding: Box<Double>? = Box(5.0)
}