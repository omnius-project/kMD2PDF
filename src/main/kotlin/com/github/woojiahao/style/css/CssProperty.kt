package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings.Theme
import kotlin.reflect.KProperty

class CssProperty<T>(defaultValue: T, private val theme: Theme = Theme.LIGHT) {
  var lightThemeProperty: T = defaultValue
  var darkThemeProperty: T = defaultValue

  operator fun getValue(thisRef: Any?, property: KProperty<*>) =
    when (theme) {
      Theme.LIGHT -> lightThemeProperty
      Theme.DARK -> darkThemeProperty
    }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    when(theme) {
      Theme.LIGHT -> {
        this.lightThemeProperty = value
      }
      Theme.DARK -> {
        this.darkThemeProperty = value
      }
    }
  }
}

inline fun <T> cssProperty(
  defaultValue: T,
  theme: Theme = Theme.LIGHT,
  configuration: CssProperty<T>.() -> Unit
) = CssProperty(defaultValue, theme).apply { configuration() }