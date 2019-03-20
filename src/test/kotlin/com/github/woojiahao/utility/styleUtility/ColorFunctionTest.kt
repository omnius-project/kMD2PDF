package com.github.woojiahao.utility.styleUtility

import com.github.woojiahao.utility.c
import org.junit.Test

class ColorFunctionTest {
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
}