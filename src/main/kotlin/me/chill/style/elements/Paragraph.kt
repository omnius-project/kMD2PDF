package me.chill.style.elements

import me.chill.style.FontFamily

open class Paragraph(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("sans-serif")
) : Element(fontSize, fontFamily)