package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.FontFamily
import com.github.woojiahao.style.elements.Element

/**
 * <tbody></tbody> element.
 */
class TableBody(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("tbody", fontSize, fontFamily)