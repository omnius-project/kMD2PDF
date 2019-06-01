package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT
import kotlin.reflect.KProperty

class CssProperty<T>(
  private val settings: Settings,
  private var light: T? = null,
  private var dark: T? = light,
  private val fallback: T? = null
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>) =
    when (settings.theme) {
      LIGHT -> light
      DARK -> dark
    } ?: fallback

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    when (settings.theme) {
      LIGHT -> {
        this.light = value
      }
      DARK -> {
        this.dark = value
      }
    }
  }
}