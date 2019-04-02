package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme
import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT
import com.github.woojiahao.style.settings
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.MONOSPACE
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.SANS_SERIF
import com.github.woojiahao.style.utility.px
import com.github.woojiahao.utility.c
import org.junit.Test
import kotlin.test.assertEquals

class CssPropertyTest {
  @Test
  fun `Dark value falls back onto light value if not provided`() {
    test {
      val color = c("FF")
      val property by CssProperty(color)
      testDarkThemeValue(color, property)
    }
  }

  @Test
  fun `Fallback value used if neither value is available aka null value`() {
    test {
      val property by CssProperty(fallback = 10)
      assertEquals(10, property)
    }
  }

  @Test
  fun `Configuring dark value does not influence light value`() {
    test {
      val lightValue = 10
      val darkValue = 20
      var property by CssProperty(lightValue)
      testLightThemeValue(lightValue, property)

      Settings.theme = DARK
      property = darkValue

      Settings.theme = LIGHT
      testLightThemeValue(lightValue, property)

      Settings.theme = DARK
      testDarkThemeValue(darkValue, property)
    }
  }

  @Test
  fun `Configuring light value does not influence dark value`() {
    test {
      val lightValue = "Foo"
      val secondaryValue = "Bar"
      var property by CssProperty(secondaryValue)
      testLightThemeValue(secondaryValue, property)

      Settings.theme = LIGHT
      property = lightValue

      Settings.theme = LIGHT
      testLightThemeValue(lightValue, property)

      Settings.theme = DARK
      testDarkThemeValue(secondaryValue, property)
    }
  }

  private fun test(test: () -> Unit) {
    Settings.reset()
    test()
    Settings.reset()
  }

  private fun <T> testDarkThemeValue(expected: T?, actual: T?) = testThemeValue(DARK, expected, actual)

  private fun <T> testLightThemeValue(expected: T?, actual: T?) = testThemeValue(LIGHT, expected, actual)

  private fun <T> testThemeValue(theme: Theme, expected: T?, actual: T?) {
    Settings.theme = theme
    assertEquals(expected, actual)
    Settings.reset()
  }
}