package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings.Theme
import com.github.woojiahao.style.Settings.theme
import kotlin.reflect.KProperty

class CssProperty<T>(
  private var light: T? = null,
  private var dark: T? = light,
  private val fallback: T? = null
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>) =
    when (theme) {
      Theme.LIGHT -> light ?: fallback
      Theme.DARK -> dark ?: fallback
    }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    when (theme) {
      Theme.LIGHT -> {
        this.light = value
      }
      Theme.DARK -> {
        this.dark = value
      }
    }
  }
}