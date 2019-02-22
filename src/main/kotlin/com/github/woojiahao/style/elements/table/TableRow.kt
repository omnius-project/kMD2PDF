package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.elements.Element

/**
 * <tr></tr> element.
 */
class TableRow(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("tr", fontSize, fontFamily)