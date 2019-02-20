package com.github.woojiahao.style.elements

import com.github.woojiahao.style.FontFamily

class Paragraph(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("p", fontSize, fontFamily)