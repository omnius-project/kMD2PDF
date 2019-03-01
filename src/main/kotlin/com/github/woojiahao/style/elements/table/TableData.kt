package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import java.awt.Color

/**
 * <td></td> element.
 */
class TableData(fontSize: Double, fontFamily: FontFamily) : Element("td", fontSize, fontFamily) {
  override var border: BorderBox? = BorderBox(Border(1.0, SOLID, Color.BLACK))
  override var padding: Box<Double>? = Box(5.0)
}