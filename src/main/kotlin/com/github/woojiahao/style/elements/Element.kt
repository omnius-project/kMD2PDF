package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.css.cssProperty
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import com.github.woojiahao.utility.px
import java.awt.Color

open class Element(private val elementName: String, settings: Settings) {

  enum class FontWeight {
    NORMAL, BOLD, BOLDER, LIGHTER
  }

  enum class TextDecoration {
    OVERLINE, LINE_THROUGH, UNDERLINE, NONE;

    fun toCss() = this.name.replace("_", "-").toLowerCase()
  }

  open var fontSize by cssProperty(settings.fontSize, settings.theme)
  open var fontFamily by cssProperty(settings.font, settings.theme)
  open var textColor by cssProperty(c("212121"), settings.theme) {
    darkThemeProperty = c("FAFAFA")
  }
  open var backgroundColor by cssProperty<Color?>(null, settings.theme)
  open var fontWeight by cssProperty<FontWeight?>(null, settings.theme)
  open var textDecoration by cssProperty<TextDecoration?>(null, settings.theme)
  open var border by cssProperty<BorderBox?>(null, settings.theme)
  open var borderRadius by cssProperty<Box<Double>?>(null, settings.theme)
  open var padding by cssProperty<Box<Double>?>(null, settings.theme)
  open var margin by cssProperty<Box<Double>?>(null, settings.theme)

  val globalCss by lazy {
    cssSelector(elementName) {
      attributes {
        "font-size" to fontSize.px
        "font-family" to fontFamily
        "color" to textColor?.cssColor()
        "background-color" to backgroundColor?.cssColor()
        "font-weight" to fontWeight?.name?.toLowerCase()
        "text-decoration" to textDecoration?.toCss()
        "border-radius" to borderRadius?.toCss { it.px }
        "padding" to padding?.toCss { it.px }
        "margin" to margin?.toCss { it.px }
        "border-top" to border?.top
        "border-right" to border?.right
        "border-bottom" to border?.bottom
        "border-left" to border?.left
      }
    }
  }

  protected val css = mutableListOf<CssSelector>()

  fun fontFamily(load: FontFamily.() -> Unit) {
    fontFamily.emptyFontFamily()
    fontFamily.load()
  }

  fun border(load: BorderBox.() -> Unit) = border?.load()

  open fun toCss(): String {
    css.add(globalCss)
    return css.joinToString("\n\n") { it.toCss() }
  }
}