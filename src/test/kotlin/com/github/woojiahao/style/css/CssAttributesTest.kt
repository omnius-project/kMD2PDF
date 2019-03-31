package com.github.woojiahao.style.css

import com.github.woojiahao.style.utility.px
import org.junit.Test
import kotlin.test.assertEquals

class CssAttributesTest {
  private class Dummy(private val name: String, private val age: Int) {
    override fun toString() = "$name is $age years old"
  }

  @Test
  fun `Empty CssAttributes returns empty attrs map`() {
    val attributes = CssAttributes()
    val expectedMap = mapOf<String, String?>()
    assertEquals(expectedMap, attributes.attrs)
  }

  @Test
  fun `add will add the attribute pair to the attributes list and return the calling object`() {
    val attributes = CssAttributes()
    attributes.add("font-weight", "bold")
    val expectedMap = mapOf<String, String?>(
      "font-weight" to "bold"
    )
    assertEquals(expectedMap, attributes.attrs)
    assertEquals(attributes, attributes)
  }

  @Test
  fun `add calls toString implementation for the value of the attribute pair`() {
    val dummyUser = Dummy("Tim", 29)
    val attributes = CssAttributes()
    attributes.add("user", dummyUser)
    val expectedMap = mapOf<String, String?>(
      "user" to "Tim is 29 years old"
    )
    assertEquals(expectedMap, attributes.attrs)
  }

  @Test
  fun `remove will remove the attribute pair from the attributes list and return the calling object`() {
    val attributes = CssAttributes()
    attributes.add("font-weight", "bold")
    attributes.remove("font-weight")
    val expectedMap = mapOf<String, String?>()
    assertEquals(expectedMap, attributes.attrs)
    assertEquals(attributes, attributes)
  }

  @Test
  fun `infix to adds attributes to attribute map`() {
    val attributes = attributes { "font-weight" to "bold" }
    val expectedMap = mapOf<String, String?>(
      "font-weight" to "bold"
    )
    assertEquals(expectedMap, attributes.attrs)
  }

  @Test
  fun `append adds the attributes of the new CssAttribute to the calling CssAttribute`() {
    val mainAttributes = attributes {
      "font-weight" to "bold"
      "font-family" to "Roboto"
    }
    val additionalAttributes = attributes {
      "font-size" to 16.px
    }
    val beforeMap = mapOf<String, String?>(
      "font-weight" to "bold",
      "font-family" to "Roboto"
    )
    assertEquals(beforeMap, mainAttributes.attrs)

    mainAttributes.append(additionalAttributes)
    val afterMap = mapOf<String, String?>(
      "font-weight" to "bold",
      "font-family" to "Roboto",
      "font-size" to "16px"
    )
    assertEquals(afterMap, mainAttributes.attrs)
  }

  @Test
  fun `toCss returns all non-null attributes in CssAttributes in CSS format`() {
    val attributes = attributes {
      "font-weight" to "bold"
      "font-size" to 16.px
      "font-family" to "Roboto"
      "color" to null
    }

    val expectedCss = listOf(
      "font-weight: bold",
      "font-size: 16px",
      "font-family: Roboto"
    ).joinToString("\n") { "\t$it;" }
    assertEquals(expectedCss, attributes.toCss())
  }

  private fun attributes(attributes: CssAttributes.() -> Unit) =
    CssAttributes().apply(attributes)
}