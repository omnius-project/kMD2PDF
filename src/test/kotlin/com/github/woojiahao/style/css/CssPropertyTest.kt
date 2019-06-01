package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme
import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT
import com.github.woojiahao.utility.c
import org.junit.Test
import kotlin.test.assertEquals

class CssPropertyTest {

  @Test
  fun `Dark value falls back onto light value if not provided`() {
    settingsTest {
      val color = c("FF")
      val property by CssProperty(it, color)
      testDarkThemeValue(it, color, property)
    }
  }

  @Test
  fun `Fallback value used if neither value is available aka null value`() {
    settingsTest {
      val property by CssProperty(it, fallback = 10)
      assertEquals(10, property)
    }
  }

  @Test
  fun `Configuring dark value does not influence light value`() {
    settingsTest {
      val lightValue = 10
      val darkValue = 20
      var property by CssProperty(it, lightValue)
      testLightThemeValue(it, lightValue, property)

      it.theme = DARK
      property = darkValue

      it.theme = LIGHT
      testLightThemeValue(it, lightValue, property)

      it.theme = DARK
      testDarkThemeValue(it, darkValue, property)
    }
  }

  @Test
  fun `Configuring light value does not influence dark value`() {
    settingsTest {
      val lightValue = "Foo"
      val secondaryValue = "Bar"
      var property by CssProperty(it, secondaryValue)
      testLightThemeValue(it, secondaryValue, property)

      it.theme = LIGHT
      property = lightValue

      it.theme = LIGHT
      testLightThemeValue(it, lightValue, property)

      it.theme = DARK
      testDarkThemeValue(it, secondaryValue, property)
    }
  }

  private fun settingsTest(test: (Settings) -> Unit) = test(Settings())

  private fun <T> testDarkThemeValue(settings: Settings, expected: T?, actual: T?) =
    testThemeValue(settings, DARK, expected, actual)

  private fun <T> testLightThemeValue(settings: Settings, expected: T?, actual: T?) =
    testThemeValue(settings, LIGHT, expected, actual)

  private fun <T> testThemeValue(settings: Settings, theme: Theme, expected: T?, actual: T?) {
    with(settings) {
      this.theme = theme
      assertEquals(expected, actual)
      reset()
    }
  }
}