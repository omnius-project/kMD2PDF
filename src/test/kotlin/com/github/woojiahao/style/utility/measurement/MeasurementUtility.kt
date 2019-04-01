package com.github.woojiahao.style.utility.measurement

import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.match

private val potentialIntInputs = listOf(Int.MIN_VALUE, Int.MAX_VALUE, 0, 123456, -123456)
private val potentialDoubleInputs = listOf(Double.MIN_VALUE, Double.MAX_VALUE, 0.0, 1234.56, -1234.56)
private val potentialFloatInputs = listOf(Float.MIN_VALUE, Float.MAX_VALUE, 0.0f, 1234.56f, -1234.56f)
private val potentialShortInputs = listOf(Short.MIN_VALUE, Short.MAX_VALUE, 0, 1234, -1234)
private val potentialByteInputs = listOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 0, 12, -12)
private val potentialLongInputs = listOf(Long.MIN_VALUE, Long.MAX_VALUE, 0, 12345678, -12345678)
private val allInputs: List<List<Number>> = listOf(
  potentialByteInputs,
  potentialShortInputs,
  potentialIntInputs,
  potentialLongInputs,
  potentialFloatInputs,
  potentialDoubleInputs
)

fun assertAllMeasurements(assert: (Measurement.Type, Number, Measurement<*>?) -> Unit) {
  Measurement.Type.values().forEach {
    assertMeasurement(it) { num, measurement ->
      assert(it, num, measurement)
    }
  }
}

fun assertMeasurement(type: Measurement.Type, assert: (Number, Measurement<*>?) -> Unit) {
  allInputs.forEach {
    it.forEach { num ->
      val measurement = when (num) {
        is Int -> num match type
        is Long -> num match type
        is Double -> num match type
        is Float -> num match type
        is Short -> num match type
        is Byte -> num match type
        else -> null
      }
      assert(num, measurement)
    }
  }
}