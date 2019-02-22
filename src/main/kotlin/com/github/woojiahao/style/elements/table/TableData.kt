package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.elements.Element
import java.awt.Color

/**
 * <td></td> element.
 */
class TableData(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("td", fontSize, fontFamily) {
  override var border = BorderBox(
    Border(
      1.0,
      Border.BorderStyle.SOLID,
      Color.BLACK
    )
  )
  override var padding: Box<Double>? = Box(5.0)
}