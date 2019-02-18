package me.chill.style.elements.lists

import me.chill.style.FontFamily
import me.chill.style.elements.Element

open class List(
  elementName: String,
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element(elementName, fontSize, fontFamily) {

  enum class ListStylePosition {
    INSIDE, OUTSIDE
  }

  enum class ListStyleType {
    CIRCLE,
    DISC,
    SQUARE,
    ARMENIAN,
    CJK_IDEOGRAPHIC,
    DECIMAL,
    DECIMAL_LEADING_ZERO,
    GEORGIAN,
    HEBREW,
    HIRAGANA,
    HIRAGANA_IROHA,
    KATAKANA,
    KATAKANA_IROHA,
    LOWER_ALPHA,
    LOWER_GREEK,
    LOWER_LATIN,
    LOWER_ROMAN,
    UPPER_ALPHA,
    UPPER_GREEK,
    UPPER_LATIN,
    UPPER_ROMAN,
    NONE;

    fun toCss() = name.toLowerCase().replace("_", "-")
  }

  open var listStyleType = ListStyleType.CIRCLE

  open var listStylePosition = ListStylePosition.OUTSIDE

  override fun toCss(): String {
    attributes.attribute("list-style-type", listStyleType.toCss())
    attributes.attribute("list-style-position", listStylePosition.name.toLowerCase())
    return super.toCss()
  }
}