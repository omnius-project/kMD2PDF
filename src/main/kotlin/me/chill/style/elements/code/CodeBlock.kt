package me.chill.style.elements.code

import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.MONOSPACE
import me.chill.style.elements.code.Code

class CodeBlock(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(MONOSPACE)
) : Code("pre", fontSize, fontFamily)