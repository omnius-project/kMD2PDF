package me.chill.elements

import java.awt.Color

/**
 * Represents a HTML element
 */
open class Element {
  open val fontSize = 16.0
  open val fontColor = Color.BLACK
  open val fontFamily = listOf("Arial")
  open val backgroundColor: Color? = null
  open val fontWeight = FontWeight.NORMAL

  enum class FontWeight {
    NORMAL, BOLD, BOLDER, LIGHTER
  }

  fun getFontFamilyString() = fontFamily.joinToString(", ") { "\"$it\"" }

  fun getFontSizeString() = "${fontSize}px"
}