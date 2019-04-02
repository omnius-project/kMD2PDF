package com.github.woojiahao.style.utility.measurement

import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.Measurement.Type.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

/**
 * For a [Measurement], we have 3 components, the data type of the value, the value and the [Measurement.Type] and each
 * test will vary these 3.
 *
 * @author Woo Jia Hao
 */
class EqualsTest {
  private class Dummy(val name: String, val age: Int) {
    override fun toString() = "$name is $age years old"
  }

  @Test
  fun `Returns false if comparing object is not the same type`() {
    val comparingObject = Dummy("Tim", 20)
    assertAllMeasurements { _, _, measurement ->
      measurement ?: return@assertAllMeasurements
      assertFalse(measurement.equals(comparingObject))
    }
  }

  @Test
  fun `Returns false if Measurements have the same data type and measurement type but different value`() {
    assertAllMeasurements { type, number, measurement ->
      val comparisonConstant = 15
      val t = comparisonConstant.matchValueType(number)
      val newMeasurement = Measurement(t, type)
      assertNotEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `Returns false if Measurements have the same value and measurement type but different data type`() {
    assertAllMeasurements { type, number, measurement ->
      measurement ?: return@assertAllMeasurements
      val newValue = number.differValueType()
      val newMeasurement = Measurement(newValue, type)
      assertFalse(measurement.equals(newMeasurement))
    }
  }

  @Test
  fun `Returns false if Measurements have the same value and data type but different measurement type`() {
    assertAllMeasurements { type, number, measurement ->
      measurement ?: return@assertAllMeasurements
      val newType = type.differType()
      val newMeasurement = Measurement(number, newType)
      assertNotEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `Returns false if Measurements has different value, data type and measurement type`() {
    assertAllMeasurements { type, _, measurement ->
      val comparisonConstant = 15
      val newValue = comparisonConstant.differValueType()
      val newType = type.differType()
      val newMeasurement = Measurement(newValue, newType)
      assertNotEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `Returns true if comparing Measurements match completely`() {
    assertAllMeasurements { type, number, measurement ->
      val newMeasurement = Measurement(number, type)
      assertEquals(measurement, newMeasurement)
    }
  }

  private fun Measurement.Type.differType() =
    when (this) {
      PIXELS -> INCHES
      INCHES -> CENTIMETERS
      CENTIMETERS -> MILLIMETERS
      MILLIMETERS -> PIXELS
    }

  private fun Number.matchValueType(toMatch: Number) =
    when (toMatch) {
      is Int -> this
      is Double -> toDouble()
      is Float -> toFloat()
      is Long -> toLong()
      is Short -> toShort()
      is Byte -> toByte()
      else -> this
    }

  private fun Number.differValueType() =
    when (this) {
      is Int, is Long, is Short, is Byte -> toDouble()
      is Double, is Float -> toInt()
      else -> this
    }
}