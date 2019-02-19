package me.chill.style.elements.code

import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.MONOSPACE
import me.chill.style.elements.code.Code

/**
 * Inline <code> element styles.
 */
class InlineCode(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(MONOSPACE)
) : Code("p code", fontSize, fontFamily)