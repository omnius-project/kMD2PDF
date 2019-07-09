package com.github.woojiahao.style.css

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme
import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT
import com.github.woojiahao.utility.c
import org.junit.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals

@Ignore
class CssPropertyTest {

  @Test
  fun `Dark value falls back onto light value if not provided`() {
    settingsTest {
      val color = c("FF")
      val property by CssProperty(this, color)
      testDarkThemeValue(color, property)
    }
  }

  @Test
  fun `Fallback value used if neither value is available aka null value`() {
    settingsTest {
      val property by CssProperty(this, fallback = 10)
      assertEquals(10, property)
    }
  }

  @Test
  fun `Configuring dark value does not influence light value`() {
    settingsTest {
      val lightValue = 10
      val darkValue = 20
      var property by CssProperty(this, lightValue)
      testLightThemeValue(lightValue, property)

      theme = DARK
      property = darkValue

      theme = LIGHT
      testLightThemeValue(lightValue, property)

      theme = DARK
      testDarkThemeValue(darkValue, property)
    }
  }

  @Test
  fun `Configuring light value does not influence dark value`() {
    settingsTest {
      val lightValue = "Foo"
      val secondaryValue = "Bar"
      var property by CssProperty(this, secondaryValue)
      testLightThemeValue(secondaryValue, property)

      theme = LIGHT
      property = lightValue

      theme = LIGHT
      testLightThemeValue(lightValue, property)

      theme = DARK
      testDarkThemeValue(secondaryValue, property)
    }
  }

  private fun settingsTest(test: Settings.() -> Unit) = Settings().test()

  private fun <T> Settings.testDarkThemeValue(expected: T?, actual: T?) =
    testThemeValue(DARK, expected, actual)

  private fun <T> Settings.testLightThemeValue(expected: T?, actual: T?) =
    testThemeValue(LIGHT, expected, actual)

  private fun <T> Settings.testThemeValue(theme: Theme, expected: T?, actual: T?) {
    this.theme = theme
    assertEquals(expected, actual)
    reset()
  }
}