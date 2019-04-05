package com.github.woojiahao.style.utility.measurement

import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.Measurement.Type.*
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Conversion chart:
 * 1px -> 1/96in -> 127/4800cm -> 127/480mm
 * 1in -> 96px -> 2.54cm -> 25.4mm
 * 1cm -> 1/2.54in -> 4800/127px -> 10mm
 * 1mm -> 1/10cm -> 1/25.4in -> 480/127px
 */
class ConvertTest {
  @Test
  fun `Pixel conversion validation`() {
    validateAll(
      PIXELS,
      INCHES to transformAllInput { it.toDouble() / 96.0 },
      CENTIMETERS to transformAllInput { it.toDouble() / (96.0 / 2.54) },
      MILLIMETERS to transformAllInput { it.toDouble() / (96.0 / 25.4) }
    )
  }

  private fun validateAll(from: Measurement.Type, vararg inputs: Pair<Measurement.Type, List<List<Double>>>) {
    inputs.forEach { validateConversion(from, it.first, it.second) }
  }

  private fun transformAllInput(transformation: (Number) -> Double) = allInputs.map { it.map(transformation) }

  private fun validateConversion(from: Measurement.Type, to: Measurement.Type, expectedData: List<List<Double>>) {
    assertMeasurementIndexed(from) { indices, _, measurement ->
      measurement ?: return@assertMeasurementIndexed
      val converted = measurement.convert(to)
      val expectedConversion = expectedData[indices.first][indices.second]
      assertEquals(to, converted.type)
      Assert.assertEquals(expectedConversion, converted.value.toDouble(), 10.0)
    }
  }
}