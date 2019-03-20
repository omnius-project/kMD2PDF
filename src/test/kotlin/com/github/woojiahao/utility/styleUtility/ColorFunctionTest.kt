package com.github.woojiahao.utility.styleUtility

import com.github.woojiahao.utility.c
import kotlinx.html.InputType
import org.junit.Test
import java.awt.Color
import kotlin.test.assertEquals

/**
 * Unit tests for the c() function in StyleUtility.kt
 */
class ColorFunctionTest {
  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if hex code is more than 6 characters long`() {
    c("FFFFFF8")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if hex code is less than or equal to 0 characters long`() {
    c("")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if hex code is 1 character long`() {
    c("F")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if hex code is 4 characters long`() {
    c("FA431")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if hex code is 5 characters long`() {
    c("FFB2A")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if invalid letter is used`() {
    c("T09E19")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `Throw IllegalArgumentException if invalid character is used`() {
    c("F$12*9")
  }

  @Test
  fun `2 character hex code is repeated 3 times`() {
    testColor("FB", "#fbfbfb")
  }

  @Test
  fun `3 character hex code is repeated twice`() {
    testColor("123", "#123123")
  }

  @Test
  fun `6 character hex code is repeated once`() {
    testColor("FA54B2", "#fa54b2")
  }

  private fun testColor(input: String, expected: String) {
    val color = c(input)
    val hexCode = color.convertRgbToHex()
    assertEquals(expected, hexCode)
  }

  private fun Color?.convertRgbToHex(): String {
    this ?: return ""
    val hexString = Integer.toHexString(rgb).substring(2)
    return "#$hexString"
  }
}