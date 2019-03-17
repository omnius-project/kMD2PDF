package com.github.woojiahao.style.utility

class Measurement<T : Number>(val value: T, val type: Type) {
  sealed class Type(val measurement: String) {
    class Pixel : Type("px")
    class Inches : Type("in")
    class Centimeters : Type("cm")
    class Percentage : Type("%")
  }

  override fun toString() = "$value${type.measurement}"
}

val <T : Number> T.px
  get() = Measurement(this, Measurement.Type.Pixel())

val <T : Number> T.`in`
  get() = Measurement(this, Measurement.Type.Inches())

val <T : Number> T.cm
  get() = Measurement(this, Measurement.Type.Centimeters())

val <T : Number> T.percent
  get() = Measurement(this, Measurement.Type.Percentage())

