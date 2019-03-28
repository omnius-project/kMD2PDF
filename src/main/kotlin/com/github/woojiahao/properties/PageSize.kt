package com.github.woojiahao.properties

import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.Measurement.Type.*
import com.github.woojiahao.style.utility.`in`
import com.github.woojiahao.style.utility.mm

/**
 * [width] and [height] are measured in inches
 */
enum class PageSize(val sizeName: String, val width: Measurement<Double>, val height: Measurement<Double>) {
  A5("A5", measurement(148), measurement(210)),
  A4("A4", measurement(210), measurement(297)),
  A3("A3", measurement(297), measurement(420)),
  B5("B5", measurement(176), measurement(250)),
  B4("B4", measurement(250), measurement(353)),
  JIS_B5("JIS_B5", measurement(182), measurement(257)),
  JIS_B4("JIS_B4", measurement(257), measurement(364)),
  LETTER("letter", 8.5.`in`, 11.0.`in`),
  LEGAL("legal", 8.5.`in`, 14.0.`in`),
  LEDGER("ledger", 11.0.`in`, 17.0.`in`);
}

private fun measurement(millimeters: Int) = millimeters.toDouble().mm.convert(INCHES)

