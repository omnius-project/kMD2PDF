package me.chill.style.elements.table

import me.chill.style.FontFamily
import me.chill.style.elements.Element

/**
 * <thead></thead> element.
 */
class TableHead(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("thead", fontSize, fontFamily)