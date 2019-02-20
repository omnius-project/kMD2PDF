package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.Border
import com.github.woojiahao.style.BorderBox
import com.github.woojiahao.style.Box
import com.github.woojiahao.style.FontFamily
import com.github.woojiahao.style.elements.Element
import java.awt.Color

/**
 * <td></td> element.
 */
class TableData(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("td", fontSize, fontFamily) {
  override var border = BorderBox(Border(1.0, Border.BorderStyle.SOLID, Color.BLACK))
  override var padding: Box<Double>? = Box(5.0)
}