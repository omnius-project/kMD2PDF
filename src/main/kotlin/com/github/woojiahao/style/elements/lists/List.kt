package com.github.woojiahao.style.elements.lists

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.elements.Element

open class List(private val elementName: String, settings: Settings) : Element(elementName, settings) {

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

  var listStyleType = ListStyleType.CIRCLE

  var listStylePosition = ListStylePosition.OUTSIDE

  var listStyleImage: String? = null

  override fun toCss(): String {
    css.add(cssSelector(elementName) {
      attributes {
        "list-style-type" to listStyleType.toCss()
        "list-style-image" to listStyleImage?.let { "url($it)" }
        "list-style-position" to listStylePosition.name.toLowerCase()
      }
    })
    return super.toCss()
  }
}