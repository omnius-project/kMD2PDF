package com.github.woojiahao.utility

import org.junit.Test
import java.awt.Color
import kotlin.test.assertEquals

class StyleUtilityTest {
  @Test
  fun `px returns an integer in pixel format for CSS`() {
    assertEquals("10px", 10.px)
  }

  @Test
  fun `px returns a double in pixel format for CSS`() {
    assertEquals("7.6px", 7.6.px)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `c throws IllegalArgumentException if hex code is more than 6 characters long`() {
    c("FFFFFF8")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `c throws IllegalArgumentException if hex code is less than 6 characters long`() {
    c("FFFFF")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `c throws IllegalArgumentException if invalid letter is used`() {
    c("T09E19")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `c throws IllegalArgumentException if invalid character is used`() {
    c("F$12*9")
  }

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