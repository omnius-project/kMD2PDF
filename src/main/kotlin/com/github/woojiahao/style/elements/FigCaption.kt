package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.SANS_SERIF

class FigCaption(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(SANS_SERIF)
) : Element("figcaption", fontSize, fontFamily)