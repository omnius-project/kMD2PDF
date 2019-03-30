package com.github.woojiahao.style

import com.github.woojiahao.style.utility.*
import com.github.woojiahao.style.utility.Border.BorderStyle.*
import com.github.woojiahao.utility.c
import org.junit.Test
import java.awt.Color
import kotlin.test.assertEquals

class BorderTest {
  @Test
  fun `Default border is 0 width, black color, NONE and 0 radius`() {
    Border().checkBorderSettings(0.0.px, NONE, Color.BLACK)
  }

  @Test
  fun `Utility methods return respective border with specific configurations`() {
    Border
      .BorderStyle
      .values()
      .forEach { it.testUtility() }
  }

  @Test
  fun `clearBorder resets border to default`() {
    with(4.1.px dashed c("EDE7F6")) {
      checkBorderSettings(4.1.px, DASHED, c("EDE7F6"))
      clear()
      checkBorderSettings(0.0.px, NONE, Color.BLACK)
    }
  }

  @Test
  fun `toString returns CSS format for border style`() {
    val border = 3.5.px double c("33")
    val expectedCss = "3.5px double rgb(51, 51, 51)"
    assertEquals(expectedCss, border.toString())
  }

  private fun Border.BorderStyle.testUtility() {
    val borderWidth = 2.0.px
    val borderColor = Color.RED

    val border = when (this) {
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

    border.checkBorderSettings(borderWidth, this, borderColor)
  }

  private fun Border.checkBorderSettings(
    borderWidth: Measurement<Double>,
    borderStyle: Border.BorderStyle,
    borderColor: Color?
  ) {
    assertEquals(borderWidth, this.borderWidth)
    assertEquals(borderStyle, this.borderStyle)
    assertEquals(borderColor, this.borderColor)
  }
}