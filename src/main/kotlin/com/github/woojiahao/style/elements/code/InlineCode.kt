package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.FontFamily

/**
 * Inline <code> element styles.
 */
class InlineCode(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.MONOSPACE)
) : Code("p code", fontSize, fontFamily)