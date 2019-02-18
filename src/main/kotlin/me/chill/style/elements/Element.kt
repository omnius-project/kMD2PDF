package me.chill.style.elements

import me.chill.style.Border
import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF
import java.awt.Color

/**
 * Represents a HTML element
 */
open class Element(
  open var fontSize: Double,
  open var fontFamily: FontFamily = FontFamily(SANS_SERIF)
) {

  open var fontColor: Color? = Color.BLACK
  open var backgroundColor: Color? = null
  open var fontWeight = FontWeight.NORMAL
  open var textDecoration = TextDecoration.NONE
  open var border = Border()

  enum class FontWeight {
    NORMAL, BOLD, BOLDER, LIGHTER
  }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = this.name.replace("_", "-").toLowerCase()
  }

  fun fontFamily(load: FontFamily.() -> Unit) {
    fontFamily.emptyFontFamily()
    fontFamily.load()
  }

  fun border(load: Border.() -> Unit) {
    border.clearBorder()
    border.load()
  }
}