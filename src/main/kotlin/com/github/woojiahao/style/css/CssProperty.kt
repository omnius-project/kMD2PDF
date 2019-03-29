package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT
import com.github.woojiahao.style.Settings.theme
import kotlin.reflect.KProperty

class CssProperty<T>(
  private var light: T? = null,
  private var dark: T? = light,
  private val fallback: T? = null
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>) =
    when (theme) {
      LIGHT -> light
      DARK -> dark
    } ?: fallback

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    when (theme) {
      LIGHT -> {
        this.light = value
      }
      DARK -> {
        this.dark = value
      }
    }
  }
}