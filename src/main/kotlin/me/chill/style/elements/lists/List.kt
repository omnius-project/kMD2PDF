package me.chill.style.elements.lists

import me.chill.style.FontFamily
import me.chill.style.elements.Element

open class List(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("sans-serif")
) : Element(fontSize, fontFamily) {

  open var listStyleType = ListStyleType.CIRCLE

  open var listStylePosition = ListStylePosition.OUTSIDE

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
}