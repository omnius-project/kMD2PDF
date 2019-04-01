package com.github.woojiahao.style.utility.measurement

import com.github.woojiahao.style.utility.*
import com.github.woojiahao.style.utility.Measurement.Type.*
import org.junit.Test
import kotlin.test.assertEquals

class MeasurementTest {
  @Test
  fun `toString returns Measurement in CSS format`() {
    assertAllMeasurements { type, num, measurement ->
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

  @Test
  fun `convert 1 pixel to inch should return 1 over 96 inches`() {
    val pixel = 1.0.px
    val converted = pixel.convert(INCHES)
    val expected = 1.0 / 96.0
    assertEquals(expected, converted.value)
  }
}