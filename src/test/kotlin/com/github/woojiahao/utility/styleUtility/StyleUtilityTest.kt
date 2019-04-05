package com.github.woojiahao.utility.styleUtility

import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import org.junit.Test
import java.awt.Color
import kotlin.test.assertEquals

class StyleUtilityTest {
  @Test
  fun `c returns a Color object when input has no # symbol`() {
    val color = c("FA98B2")
    val expectedColor = Color.decode("#FA98B2")
    assertEquals(expectedColor, color)
  }

  @Test
  fun `c returns a Color object when input has # symbol`() {
    val color = c("#486ca5")
    val expectedColor = Color.decode("#486ca5")
    assertEquals(expectedColor, color)
  }

  @Test
  fun `cssColor returns rgb format for color`() {
    val color = c("#D32F2F")
    val rgbFormat = color?.cssColor()
    val expectedRgb = "rgb(211, 47, 47)"
    assertEquals(rgbFormat, expectedRgb)
  }
}