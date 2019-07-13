package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.css.cssSelector
import com.github.woojiahao.style.utility.*
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import java.awt.Color

open class Element(private val elementName: String, settings: Settings) {

  enum class FontWeight { NORMAL, BOLD, BOLDER, LIGHTER }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = name.replace("_", "-").toLowerCase()
  }

  private val theme = settings.theme

  var fontSize = CssProperty(theme, fallback = settings.fontSize)
  var fontFamily = CssProperty<FontFamily?>(theme, fallback = settings.font)
  var textColor = CssProperty(theme, c("21"), c("EE"))
  var backgroundColor = CssProperty<Color?>(theme)
  var fontWeight = CssProperty<FontWeight?>(theme)
  var textDecoration = CssProperty<TextDecoration?>(theme)
  var border = CssProperty(theme, fallback = BorderBox(Border()))
  var borderRadius = CssProperty(theme, fallback = Box(0.0.px))
  var padding = CssProperty<Box<Measurement<Double>>?>(theme)
  var margin = CssProperty<Box<Measurement<Double>>?>(theme)

  val globalCss
    get() = cssSelector(elementName) {
      attributes {
        "font-size" to fontSize.value?.let { it }
        "font-family" to fontFamily
        "color" to textColor.value?.cssColor()
        "background-color" to backgroundColor.value?.cssColor()
        "font-weight" to fontWeight.value?.name?.toLowerCase()
        "text-decoration" to textDecoration.value?.toCss()
        "border-radius" to borderRadius.value?.toCss { it.toString() }
        "padding" to padding.value?.toCss { it.toString() }
        "margin" to margin.value?.toCss { it.toString() }
        "border-top" to border.value?.top
        "border-right" to border.value?.right
        "border-bottom" to border.value?.bottom
        "border-left" to border.value?.left
      }
    }

  val attributes = listOf(
    fontSize,
    fontFamily,
    textColor,
    backgroundColor,
    fontWeight,
    textDecoration,
    border,
    borderRadius,
    padding,
    margin
  )

  protected val css = mutableListOf<CssSelector>()

  fun fontFamily(load: FontFamily.() -> Unit) {
    fontFamily.value?.clear()
    fontFamily.value?.load()
  }

  fun border(load: BorderBox.() -> Unit) = border.value?.load()

  open fun toCss(): String {
    css += globalCss
    return css.joinToString("\n\n") { it.toCss() }
  }
}