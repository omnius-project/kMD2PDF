package me.chill.style.elements.headers

import me.chill.style.FontFamily

class HeaderOne(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Header("h1", fontSize, fontFamily, 2.0)