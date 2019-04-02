package com.github.woojiahao.style.css

import com.github.woojiahao.style.utility.FontFamily
import org.junit.Test
import kotlin.test.assertEquals

// TODO: Properly format the nested CSS selectors to unit test them
class CssSelectorTest {
  @Test
  fun `toCss returns empty element body if no attributes are provided`() {
    val css = cssSelector("p") { }
    val expectedCss = createExpectedCss(
      "p {",
      "}"
    )
    assertEquals(expectedCss, css.toCss())
  }

  @Test
  fun `toCss returns CSS formatted text with attributes`() {
    val css = cssSelector("p") {
      attributes {
        "font-weight" to "bold"
        "font-family" to FontFamily("Roboto Mono", "Fira Code")
      }
    }
    val expectedCss = createExpectedCss(
      "p {",
      "\tfont-weight: bold;",
      "\tfont-family: 'Roboto Mono', 'Fira Code';",
      "}"
    )
    assertEquals(expectedCss, css.toCss())
  }

  private fun createExpectedCss(vararg lines: String) = lines.joinToString("\n")
}