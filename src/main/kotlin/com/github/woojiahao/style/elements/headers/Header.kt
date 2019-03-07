package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element

open class Header(
  headerName: String,
  settings: Settings,
  headerScaleFactor: Double = 1.0
) : Element(headerName, settings) {
  override var fontSize = super.fontSize.times(headerScaleFactor)
}
