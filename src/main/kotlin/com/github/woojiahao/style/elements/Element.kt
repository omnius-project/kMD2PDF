package com.github.woojiahao.style.elements

import com.github.woojiahao.style.css.CssAttributes
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.px
import java.awt.Color

/**
 * Represents a HTML element
 */
open class Element(
  private val elementName: String,
  open var fontSize: Double,
  open var fontFamily: FontFamily
) {

  enum class FontWeight {
    NORMAL, BOLD, BOLDER, LIGHTER
  }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = this.name.replace("_", "-").toLowerCase()
  }

  open var textColor: Color? = Color.BLACK
  open var backgroundColor: Color? = null
  open var fontWeight = FontWeight.NORMAL
  open var textDecoration = TextDecoration.NONE
  open var border = BorderBox(Border())
  open var borderRadius = Box(0.0)
  open var padding: Box<Double>? = null
  open var margin: Box<Double>? = null

  protected val attributes: CssAttributes by lazy {
    CssAttributes()
      .add("font-size", fontSize.px)
      .add("font-family", fontFamily.toString())
      .add("color", textColor?.cssColor())
      .add("background-color", backgroundColor?.cssColor())
      .add("font-weight", fontWeight.name.toLowerCase())
      .add("text-decoration", textDecoration.toCss())
      .add("border-radius", borderRadius.toCss { it.px })
      .add("padding", padding?.toCss { it.px })
      .add("margin", margin?.toCss { it.px })
      .add("border-top", border.top.toString())
      .add("border-right", border.right.toString())
      .add("border-bottom", border.bottom.toString())
      .add("border-left", border.left.toString())
  }

  fun fontFamily(load: FontFamily.() -> Unit) {
    fontFamily.emptyFontFamily()
    fontFamily.load()
  }

  fun border(load: BorderBox.() -> Unit) = border.load()

  open fun toCss() =
    StringBuilder("$elementName {\n")
      .append(attributes.toCss())
      .append("\n}")
      .toString()
}