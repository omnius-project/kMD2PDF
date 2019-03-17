package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Measurement.Type.*

open class Header(headerName: String, private val headerScaleFactor: Double = 1.0) : Element(headerName) {
  override var fontSize = super.fontSize
    get() {
      val scaledSize = super.fontSize.value.times(headerScaleFactor)
      return when (super.fontSize.type) {
        PIXELS -> scaledSize.px
        INCHES -> scaledSize.`in`
        CENTIMETERS -> scaledSize.cm
        PERCENTAGE -> scaledSize.percent
        else -> scaledSize.px
      }
    }
}
