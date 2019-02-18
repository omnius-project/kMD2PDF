package me.chill.style.elements

import me.chill.style.*
import me.chill.utility.cssColor
import me.chill.utility.px
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

  open var fontColor: Color? = Color.BLACK
  open var backgroundColor: Color? = null
  open var fontWeight = FontWeight.NORMAL
  open var textDecoration = TextDecoration.NONE
  open var border = BorderBox(Border())
  open var borderRadius = Box(0.0)
  open var padding: Box<Double>? = null
  open var margin: Box<Double>? = null

  protected val attributes: CSSAttributeManager by lazy {
    CSSAttributeManager()
      .attribute("font-size", fontSize.px)
      .attribute("font-family", fontFamily.toString())
      .attribute("color", fontColor?.cssColor())
      .attribute("background-color", backgroundColor?.cssColor())
      .attribute("font-weight", fontWeight.name.toLowerCase())
      .attribute("text-decoration", textDecoration.toCss())
      .attribute("border-radius", borderRadius.toCss { it.px })
      .attribute("padding", padding?.toCss { it.px })
      .attribute("margin", margin?.toCss { it.px })
      .attribute("border-top", border.top.toString())
      .attribute("border-right", border.right.toString())
      .attribute("border-bottom", border.bottom.toString())
      .attribute("border-left", border.left.toString())
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