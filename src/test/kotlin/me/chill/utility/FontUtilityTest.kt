package me.chill.utility

import org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS
import org.junit.Test
import kotlin.test.assertEquals

// TODO: Add more asserts for Linux and Mac OS
class FontUtilityTest {

  private val fontDirectories = getFontDirectories()

  @Test
  fun `getFontDirectories on Windows will return the fonts folder`() {
    if (IS_OS_WINDOWS) {
      val expectedFontDirectories = listOf("${System.getenv("WINDIR")}\\Fonts")
      assertEquals(expectedFontDirectories, fontDirectories)
    }
  }
}