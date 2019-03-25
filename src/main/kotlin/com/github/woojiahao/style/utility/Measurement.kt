package com.github.woojiahao.style.utility

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

class Measurement<T : Number>(val value: T, val type: Type) {
  sealed class Type(val measurement: String) {
    class Pixel : Type("px")
    class Inches : Type("in")
    class Centimeters : Type("cm")
    class Percentage : Type("%")
  }

  override fun toString() = "$value${type.measurement}"

  override fun equals(other: Any?): Boolean {
    if (other !is Measurement<*>) return false

    val measurement = other as Measurement<T>

    return EqualsBuilder()
      .append(value, measurement.value)
      .append(type.measurement, measurement.type.measurement)
      .isEquals
  }

  override fun hashCode() =
    HashCodeBuilder(17, 37)
      .append(value)
      .append(type.measurement)
      .toHashCode()
}

val <T : Number> T.px
  get() = Measurement(this, Measurement.Type.Pixel())

val <T : Number> T.`in`
  get() = Measurement(this, Measurement.Type.Inches())

val <T : Number> T.cm
  get() = Measurement(this, Measurement.Type.Centimeters())

val <T : Number> T.percent
  get() = Measurement(this, Measurement.Type.Percentage())

