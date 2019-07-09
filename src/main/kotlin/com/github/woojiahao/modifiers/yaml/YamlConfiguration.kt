package com.github.woojiahao.modifiers.yaml

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.Measurement

data class YamlConfiguration(
  val font: FontFamily? = null,
  val monospaceFont: FontFamily? = null,
  val fontSize: Measurement<Double>? = null,
  val theme: Settings.Theme? = null
) {
  constructor(
    font: List<String>?,
    monospaceFont: List<String>?,
    fontSize: Int?,
    theme: String?
  ) : this(
    font?.let { FontFamily(*it.toTypedArray()) },
    monospaceFont?.let { FontFamily(*it.toTypedArray()) },
    fontSize?.let { Measurement(it.toDouble(), Measurement.Type.PIXELS) },
    theme?.let { Theme.valueOf(it.toUpperCase()) }
  )
}