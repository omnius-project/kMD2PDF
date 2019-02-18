package me.chill.style.elements

import me.chill.style.FontFamily

class Paragraph(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element(fontSize, fontFamily)