package com.github.woojiahao.style.utility.measurement

import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.Measurement.Type.*
import com.github.woojiahao.style.utility.match
import org.junit.Test
import kotlin.test.assertEquals

class InfixMeasurementTest {
  @Test
  fun `match pairs the value with measurement type and returns a Measurement`() {
    assertAllMeasurements { type, number, measurement ->
      val newMeasurement = number match type
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `px returns Measurement with given value and type of PIXELS`() {
    assertMeasurement(PIXELS) { num, measurement ->
      val newMeasurement = Measurement(num, PIXELS)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `in returns Measurement with given value and type of INCHES`() {
    assertMeasurement(INCHES) { num, measurement ->
      val newMeasurement = Measurement(num, INCHES)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `cm returns Measurement with given value and type of CENTIMETERS`() {
    assertMeasurement(CENTIMETERS) { num, measurement ->
      val newMeasurement = Measurement(num, CENTIMETERS)
      assertEquals(measurement, newMeasurement)
    }
  }

  @Test
  fun `mm returns Measurement with given value and type of MILLIMETERS`() {
    assertMeasurement(MILLIMETERS) { num, measurement ->
      val newMeasurement = Measurement(num, MILLIMETERS)
      assertEquals(measurement, newMeasurement)
    }
  }
}