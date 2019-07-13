package com.github.woojiahao.style.elements.headers

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.match

open class Heading(
  headingName: String,
  headingScaleFactor: Double = 1.0,
  settings: Settings
) : Element(headingName, settings) {
  init {
    fontSize.value = super.fontSize.value.let {
      it ?: null
      with(it!!) {
        value.times(headingScaleFactor) match type
      }
    }
  }
}
