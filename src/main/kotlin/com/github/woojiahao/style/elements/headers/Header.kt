package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.*
import com.github.woojiahao.style.utility.Measurement.Type.*

open class Header(headerName: String, private val headerScaleFactor: Double = 1.0) : Element(headerName) {
  init {
    fontSize = calculateScaledFontSize()
  }

  private fun calculateScaledFontSize(): Measurement<Double>? {
    val scaledSize = super.fontSize?.value?.times(headerScaleFactor)
    return when (super.fontSize?.type) {
      is Pixel -> scaledSize?.px
      is Inches -> scaledSize?.`in`
      is Centimeters -> scaledSize?.cm
      is Percentage -> scaledSize?.percent
      null -> null
    }
  }
}
