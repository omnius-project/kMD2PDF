package me.chill.style.elements.headers

import me.chill.style.FontFamily

class HeaderTwo(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Header("h2", fontSize, fontFamily, 1.5)