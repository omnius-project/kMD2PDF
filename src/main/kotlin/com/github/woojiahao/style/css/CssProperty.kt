package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT

class CssProperty<T>(
  var theme: Settings.Theme,
  private var light: T? = null,
  private var dark: T? = light,
  private var fallback: T? = null
) {
  var value: T?
    get() = when (theme) {
      LIGHT -> light
      DARK -> dark
    } ?: fallback
    set(input) {
      light = input
      dark = input
    }
}