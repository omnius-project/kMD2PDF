package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.utility.FontFamily

class HeaderOne(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Header("h1", fontSize, fontFamily, 2.0)