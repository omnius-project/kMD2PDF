package com.github.woojiahao.style.utility

import com.github.woojiahao.style.utility.Measurement.Type.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class MeasurementTest {
  private class Dummy(val name: String, val age: Int) {
    override fun toString() = "$name is $age years old"
  }

  private val potentialIntInputs = listOf(Int.MIN_VALUE, Int.MAX_VALUE, 0, 9999, -1234)
  private val potentialDoubleInputs = listOf(Double.MIN_VALUE, Double.MAX_VALUE, 0.0, 9999.99, -1234.56)
  private val potentialFloatInputs = listOf(Float.MIN_VALUE, Float.MAX_VALUE, 0.4f, 9999.99f, -1234.56f)
  private val potentialShortInputs = listOf(Short.MIN_VALUE, Short.MAX_VALUE, 0, 9999, -1234)
  private val potentialByteInputs = listOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 0, 56, -27)
  private val potentialLongInputs = listOf(Long.MIN_VALUE, Long.MAX_VALUE, 0, 999900123, -1234343432)
  private val allInputs: List<List<Number>> = listOf(
    potentialByteInputs,
    potentialShortInputs,
    potentialIntInputs,
    potentialLongInputs,
    potentialFloatInputs,
    potentialDoubleInputs
  )

  @Test
  fun `equals returns false if comparing object is not the same type`() {
    val measurement = Measurement(10, PIXELS)
    val comparingObject = Dummy("Tim", 20)
    assertFalse(measurement.equals(comparingObject))
  }

  @Test
  fun `equals returns false if comparing Measurement does not have the same value with the same data type`() {
    val measurement = Measurement(10, PIXELS)
    val newMeasurement = Measurement(20, PIXELS)
    assertNotEquals(measurement, newMeasurement)
  }

  @Test
  fun `equals returns false if comparing Measurement value does not have the same data type`() {
    val measurement = Measurement(10.0, PIXELS)
    val newMeasurement = Measurement(10, PIXELS)
    assertFalse(measurement.equals(newMeasurement))
  }

  @Test
  fun `equals returns false if comparing Measurement does not have the same measurement type`() {
    val measurement = Measurement(20, PIXELS)
    val newMeasurement = Measurement(20, INCHES)
    assertNotEquals(measurement, newMeasurement)
  }

  @Test
  fun `equals returns false if comparing Measurement does not match the original at all`() {
    val measurement = Measurement(10, PIXELS)
    val newMeasurement = Measurement(20, INCHES)
    assertNotEquals(measurement, newMeasurement)
  }

  @Test
  fun `px returns Measurement with given value and type of PIXELS`() {
    testPossibleInputs(PIXELS) { num, measurement ->
      val newMeasurement = Measurement(num, PIXELS)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `in returns Measurement with given value and type of INCHES`() {
    testPossibleInputs(INCHES) { num, measurement ->
      val newMeasurement = Measurement(num, INCHES)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `cm returns Measurement with given value and type of CENTIMETERS`() {
    testPossibleInputs(CENTIMETERS) { num, measurement ->
      val newMeasurement = Measurement(num, CENTIMETERS)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `mm returns Measurement with given value and type of MILLIMETERS`() {
    testPossibleInputs(MILLIMETERS) { num, measurement ->
      val newMeasurement = Measurement(num, MILLIMETERS)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `toString returns Measurement in CSS format`() {
    testAllMeasurements { type, num, measurement ->
      assertEquals("$num${type.measurement}", measurement.toString())
    }
  }

  @Test(expected = IllegalArgumentException::class)
  fun `+ throws IllegalArgumentException if type of Measurements do not match`() {
    val first = 10.px
    val second = 10.`in`
    first + second
  }

  @Test
  fun `+ adds the values of both Measurements, returning a new Measurement`() {
    val first = 25.px
    val second = 39.px
    val final = first + second
    val expectedMeasurement = (25 + 39).px
    assertEquals(expectedMeasurement, final)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `- throws IllegalArgumentException if type of Measurements do not match`() {
    val first = 10.px
    val second = 10.cm
    first - second
  }

  @Test
  fun `- subtracts the values of both Measurements, returning a new Measurement`() {
    val first = 57.px
    val second = 12.px
    val final = first - second
    val expectedMeasurement = (57 - 12).px
    assertEquals(expectedMeasurement, final)
  }

  private fun testAllMeasurements(assert: (Measurement.Type, Number, Measurement<*>?) -> Unit) {
    Measurement.Type.values().forEach {
      testPossibleInputs(it) { num, measurement ->
        assert(it, num, measurement)
      }
    }
  }

  private fun testPossibleInputs(
    measurementType: Measurement.Type,
    assert: (Number, Measurement<*>?) -> Unit
  ) {
    allInputs.forEach {
      it.forEach { num ->
        val measurement = when (num) {
          is Int -> num match measurementType
          is Long -> num match measurementType
          is Double -> num match measurementType
          is Float -> num match measurementType
          is Short -> num match measurementType
          is Byte -> num match measurementType
          else -> null
        }
        assert(num, measurement)
      }
    }
  }
}