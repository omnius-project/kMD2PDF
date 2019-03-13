package com.github.woojiahao.style.utility

class Measurement<T : Number>(val value: T, val type: Type) {
  enum class Type(val measurement: String) {
    PIXELS("px"), INCHES("in"), CENTIMETERS("cm"), PERCENTAGE("%")
  }

  override fun toString() = "$value${type.measurement}"
}

val <T : Number> T.px
  get() = Measurement(this, Measurement.Type.PIXELS)

val <T : Number> T.`in`
  get() = Measurement(this, Measurement.Type.INCHES)

val <T : Number> T.cm
  get() = Measurement(this, Measurement.Type.CENTIMETERS)

val <T : Number> T.percent
  get() = Measurement(this, Measurement.Type.PERCENTAGE)

