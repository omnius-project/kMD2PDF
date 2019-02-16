package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.utility.px
import java.awt.Color

/**
 * Represents a HTML element
 */
open class Element(
  open var fontSize: Double,
  open var fontFamily: FontFamily = FontFamily("serif")
) {

  open var fontColor = Color.BLACK
  open var backgroundColor: Color? = null
  open var fontWeight = FontWeight.NORMAL
  open var textDecoration = TextDecoration.NONE

  enum class FontWeight {
    NORMAL, BOLD, BOLDER, LIGHTER
  }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = this.name.replace("_", "-").toLowerCase()
  }

  fun getFontSizeString() = fontSize.px

  fun fontFamily(load: FontFamily.() -> Unit) {
    fontFamily.emptyFontFamily()
    fontFamily.load()
  }
}