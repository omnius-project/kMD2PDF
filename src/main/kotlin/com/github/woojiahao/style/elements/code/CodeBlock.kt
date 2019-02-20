package com.github.woojiahao.style.elements.code

import com.github.woojiahao.style.FontFamily

class CodeBlock(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.MONOSPACE)
) : Code("pre", fontSize, fontFamily)