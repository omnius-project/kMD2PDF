package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.Measurement.Type.*
import com.github.woojiahao.style.utility.`in`
import com.github.woojiahao.style.utility.cm
import com.github.woojiahao.style.utility.percent
import com.github.woojiahao.style.utility.px

open class Header(
  headerName: String,
  settings: Settings,
  private val headerScaleFactor: Double = 1.0
) : Element(headerName, settings) {
  override var fontSize = super.fontSize
    get() {
      val scaledSize = super.fontSize.value.times(headerScaleFactor)
      return when (super.fontSize.type) {
        PIXELS -> scaledSize.px
        INCHES -> scaledSize.`in`
        CENTIMETERS -> scaledSize.cm
        PERCENTAGE -> scaledSize.percent
      }
    }
}
