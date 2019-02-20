package com.github.woojiahao.style.elements

import com.github.woojiahao.style.FontFamily

/**
 * <img> element.
 */
class Image(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("img", fontSize, fontFamily)