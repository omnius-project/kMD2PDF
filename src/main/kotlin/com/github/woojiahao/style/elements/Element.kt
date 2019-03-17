package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssProperty
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.utility.*
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import java.awt.Color

open class Element(elementName: String) {

  enum class FontWeight { NORMAL, BOLD, BOLDER, LIGHTER }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = name.replace("_", "-").toLowerCase()
  }

  open var fontSize by CssProperty(fallback = Settings.fontSize)
  open var fontFamily by CssProperty<FontFamily?>(fallback = Settings.font)
  open var textColor by CssProperty(c("212121"), c("EEEEEE"))
  open var backgroundColor by CssProperty<Color?>()
  open var fontWeight by CssProperty<FontWeight?>()
  open var textDecoration by CssProperty<TextDecoration?>()
  open var border by CssProperty(BorderBox(Border()))
  open var borderRadius by CssProperty<Box<Measurement<Double>>?>()
  open var padding by CssProperty<Box<Measurement<Double>>?>()
  open var margin by CssProperty<Box<Measurement<Double>>?>()

  val globalCss by lazy {
    cssSelector(elementName) {
      attributes {
        "font-size" to fontSize?.let { it }
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
    fontFamily?.clear()
    fontFamily?.load()
  }

  fun border(load: BorderBox.() -> Unit) = border?.load()

  open fun toCss(): String {
    css.add(globalCss)
    return css.joinToString("\n\n") { it.toCss() }
  }
}