package me.chill.style.elements.table

import me.chill.style.FontFamily
import me.chill.style.elements.Element

/**
 * <tbody></tbody> element.
 */
class TableBody(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element(fontSize, fontFamily)