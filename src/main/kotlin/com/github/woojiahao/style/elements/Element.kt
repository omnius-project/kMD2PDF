package com.github.woojiahao.style.elements

import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import java.awt.Color

open class Element(private val elementName: String) {

  enum class FontWeight { NORMAL, BOLD, BOLDER, LIGHTER }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = name.replace("_", "-").toLowerCase()
  }

  open var fontSize = createProperty(settings.fontSize)
  open var fontFamily = createProperty(settings.font)
  open var textColor = createProperty(c("212121"), c("EEEEEE"))
  open var backgroundColor = createProperty<Color?>(null)
  open var fontWeight = createProperty<FontWeight?>(null)
  open var textDecoration = createProperty<TextDecoration?>(null)
  open var border = createProperty<BorderBox?>(null)
  open var borderRadius = createProperty<Box<Measurement<Double>>?>(null)
  open var padding = createProperty<Box<Measurement<Double>>?>(null)
  open var margin = createProperty<Box<Measurement<Double>>?>(null)

  val globalCss by lazy {
    cssSelector(elementName) {
      attributes {
        "font-size" to fontSize
        "font-family" to fontFamily
        "color" to textColor?.cssColor()
        "background-color" to backgroundColor?.cssColor()
        "font-weight" to fontWeight?.name?.toLowerCase()
        "text-decoration" to textDecoration?.toCss()
        "border-radius" to borderRadius?.toCss { it.toString() }
        "padding" to padding?.toCss { it.toString() }
        "margin" to margin?.toCss { it.toString() }
        "border-top" to border?.top
        "border-right" to border?.right
        "border-bottom" to border?.bottom
        "border-left" to border?.left
      }
    }
  }

  protected val css = mutableListOf<CssSelector>()

  fun fontFamily(load: FontFamily.() -> Unit) {
    fontFamily.clear()
    fontFamily.load()
  }

  fun border(load: BorderBox.() -> Unit) = border?.load()

  open fun toCss(): String {
    css.add(globalCss)
    return css.joinToString("\n\n") { it.toCss() }
  }

  private fun <T> createProperty(default: T, darkTheme: T = default): T {
    val property by cssProperty(default, settings.theme) { this.darkTheme = darkTheme }
    return property
  }
}