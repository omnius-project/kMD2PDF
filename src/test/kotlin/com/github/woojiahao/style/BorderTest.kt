package com.github.woojiahao.style

import com.github.woojiahao.style.utility.*
import com.github.woojiahao.style.utility.Border.BorderStyle.*
import com.github.woojiahao.utility.c
import org.junit.Test
import java.awt.Color
import kotlin.test.Ignore
import kotlin.test.assertEquals

// TODO: Fix these test cases to accommodate the new changes made to Border
@Ignore
class BorderTest {
  @Test
  fun `Default border is 0 width, black color, NONE and 0 radius`() {
    with(Border()) {
      checkBorderSettings(0.0.px, NONE, Color.BLACK)
    }
  }

  @Test
  fun `DSL sets border style to respective value`() {
    Border
      .BorderStyle
      .values()
      .forEach { it.testDSL() }
  }

  @Test
  fun `clearBorder resets border to default`() {
    with(Border()) {
      border {
        4.1.px dashed c("EDE7F6")
      }
      clear()
      checkBorderSettings(0.0.px, NONE, Color.BLACK)
    }
  }

  private fun Border.BorderStyle.testDSL() {
    val borderWidth = 2.0.px
    val borderColor = Color.RED

    with(Border()) {
      border {
        when (this@testDSL) {
          DOTTED -> borderWidth dotted borderColor
          DASHED -> borderWidth dashed borderColor
          SOLID -> borderWidth solid borderColor
          DOUBLE -> borderWidth double borderColor
          GROOVE -> borderWidth groove borderColor
          RIDGE -> borderWidth ridge borderColor
          INSET -> borderWidth inset borderColor
          OUTSET -> borderWidth outset borderColor
          NONE -> borderWidth none borderColor
          HIDDEN -> borderWidth hidden borderColor
        }
      }

      checkBorderSettings(borderWidth, this@testDSL, borderColor)
    }
  }

  private fun Border.border(load: Border.() -> Unit) = apply { load() }

  private fun Border.checkBorderSettings(
    borderWidth: Measurement<Double>,
    borderStyle: Border.BorderStyle,
    borderColor: Color?
  ) {
    assertEquals(borderWidth.value, this.borderWidth.value)
    assertEquals(borderWidth.type.measurement, this.borderWidth.type.measurement)
    assertEquals(borderStyle, this.borderStyle)
    assertEquals(borderColor, this.borderColor)
  }
}