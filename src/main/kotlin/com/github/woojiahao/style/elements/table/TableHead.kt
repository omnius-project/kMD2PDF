package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.FontFamily

/**
 * <thead></thead> element.
 */
class TableHead(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("thead", fontSize, fontFamily)