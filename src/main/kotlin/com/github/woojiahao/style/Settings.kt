package com.github.woojiahao.style

import com.github.woojiahao.style.Settings.Theme.LIGHT
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.MONOSPACE
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.SANS_SERIF
import com.github.woojiahao.style.utility.px

class Settings {
  enum class Theme { DARK, LIGHT }

  var fontSize = 16.0.px
  var theme = LIGHT
  var font = FontFamily(SANS_SERIF)
    get() = field.clone()
  var monospaceFont = FontFamily(MONOSPACE)
    get() = field.clone()

  fun reset() {
    fontSize = 16.0.px
    theme = LIGHT
    font = FontFamily(SANS_SERIF)
    monospaceFont = FontFamily(MONOSPACE)
  }
}

inline fun settings(configuration: Settings.() -> Unit) = Settings().apply(configuration)