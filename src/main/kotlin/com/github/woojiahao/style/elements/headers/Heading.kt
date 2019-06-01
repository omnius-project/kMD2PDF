package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.match

open class Heading(
  headerName: String,
  private val headerScaleFactor: Double = 1.0,
  settings: Settings
) : Element(headerName, settings) {
  init {
    fontSize = calculateScaledFontSize()
  }

  private fun calculateScaledFontSize(): Measurement<Double>? {
    with (super.fontSize) {
      this ?: return null
      val scaledSize = value.times(headerScaleFactor)
      return scaledSize match type
    }
  }
}
