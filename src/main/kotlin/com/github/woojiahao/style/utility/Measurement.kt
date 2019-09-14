package com.github.woojiahao.style.utility

import com.github.woojiahao.style.utility.Measurement.Type.*
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

class Measurement<T : Number>(val value: T, val type: Type) {
  enum class Type(val measurement: String, val toPixel: Double = 1.0) {
    PIXELS("px"),
    INCHES("in", 96.0),
    CENTIMETERS("cm", (96.0 / 2.54)),
    MILLIMETERS("mm", (96.0 / 25.4));

    companion object {
      fun getOrNull(target: String) =
        values().firstOrNull { it.measurement.toLowerCase() == target.toLowerCase() }
    }
  }

  companion object {
    private val measurementRegex: Regex
      get() {
        val regexString = values().joinToString("|") { it.measurement }
        return Regex("^([\\d.]+)(|($regexString))\$")
      }

    fun toMeasurement(from: String): Measurement<Double>? {
      if (!measurementRegex.matches(from)) return null

      val parts = measurementRegex.matchEntire(from)?.groupValues ?: return null
      if (parts.isEmpty()) return null

      val value = parts[1]
      val unit = parts[2]

      return if (unit.isEmpty()) value.toDoubleOrNull()?.px
      else {
        val type = Type.getOrNull(unit) ?: return null
        return value.toDouble() match type
      }
    }
  }

  @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
  operator fun plus(other: Measurement<T>): Measurement<T> {
    require(type.measurement == other.type.measurement) { "Measurement types to add must be the same" }

    val num = when (value) {
      is Int -> {
        other.value as Int
        other.value + value
      }
      is Long -> {
        other.value as Long
        other.value + value
      }
      is Double -> {
        other.value as Double
        other.value + value
      }
      is Float -> {
        other.value as Float
        other.value + value
      }
      is Short -> {
        other.value as Short
        other.value + value
      }
      is Byte -> {
        other.value as Byte
        other.value + value
      }
      else -> throw IllegalStateException("Measurement type didn't line up for some reason")
    } as T

    return num match other.type
  }

  @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
  operator fun minus(other: Measurement<T>): Measurement<T> {
    require(type.measurement == other.type.measurement) { "Measurement types to add must be the same" }

    val num = when (value) {
      is Int -> {
        other.value as Int
        value - other.value
      }
      is Long -> {
        other.value as Long
        value - other.value
      }
      is Double -> {
        other.value as Double
        value - other.value
      }
      is Float -> {
        other.value as Float
        value - other.value
      }
      is Short -> {
        other.value as Short
        value - other.value
      }
      is Byte -> {
        other.value as Byte
        value - other.value
      }
      else -> throw IllegalStateException("Measurement type didn't line up for some reason")
    } as T

    return num match other.type
  }

  @Suppress("UNCHECKED_CAST")
  fun convert(to: Type = PIXELS): Measurement<T> {
    val pixelMeasurement = type.toPixel * value.toDouble()
    val targetMeasurement = pixelMeasurement / to.toPixel
    return (targetMeasurement as T) match to
  }

  override fun toString() = "$value${type.measurement}"

  @Suppress("UNCHECKED_CAST")
  override fun equals(other: Any?): Boolean {
    if (other !is Measurement<*>) return false

    other as Measurement<T>

    return EqualsBuilder()
      .append(value, other.value)
      .append(type.measurement, other.type.measurement)
      .isEquals
  }

  override fun hashCode() =
    HashCodeBuilder(17, 37)
      .append(value)
      .append(type.measurement)
      .toHashCode()
}

infix fun <T : Number> T.match(type: Measurement.Type) =
  when (type) {
    PIXELS -> this.px
    INCHES -> this.`in`
    CENTIMETERS -> this.cm
    MILLIMETERS -> this.mm
  }

val <T : Number> T.px
  get() = Measurement(this, PIXELS)

val <T : Number> T.`in`
  get() = Measurement(this, INCHES)

val <T : Number> T.cm
  get() = Measurement(this, CENTIMETERS)

val <T : Number> T.mm
  get() = Measurement(this, MILLIMETERS)
