package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.utility.FontFamily

/**
 * Inline <code> element styles.
 */
class InlineCode(
  fontSize: Double,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.MONOSPACE)
) : Code("p code", fontSize, fontFamily)