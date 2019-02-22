package com.github.woojiahao.style

import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.BorderBox
import org.junit.Test
import java.awt.Color
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BorderBoxTest {
  @Test
  fun `Borders in BorderBox are copies of the original inputs`() {
    val borderBox = BorderBox(Border())
    borderBox.checkReferences()
  }

  @Test
  fun `top changes only top Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        top {
          10.0 dashed Color.RED
        }
      }

      checkAllBorderSettings(topBorder = Border(
        10.0,
        Border.BorderStyle.DASHED,
        Color.RED
      )
      )
    }
  }

  @Test
  fun `right changes only right Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        right {
          10.0 dashed Color.RED
        }
      }

      checkAllBorderSettings(rightBorder = Border(
        10.0,
        Border.BorderStyle.DASHED,
        Color.RED
      )
      )
    }
  }

  @Test
  fun `bottom changes only bottom Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        bottom {
          10.0 dashed Color.RED
        }
      }

      checkAllBorderSettings(bottomBorder = Border(
        10.0,
        Border.BorderStyle.DASHED,
        Color.RED
      )
      )
    }
  }

  @Test
  fun `left changes only left Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        left {
          10.0 dashed Color.RED
        }
      }

      checkAllBorderSettings(leftBorder = Border(
        10.0,
        Border.BorderStyle.DASHED,
        Color.RED
      )
      )
    }
  }


  @Test
  fun `all changes all Borders`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        all {
          10.0 dashed Color.RED
        }
      }

      checkAllBorderSettings(
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        )
      )
    }
  }

  @Test
  fun `top after all overrides all for top Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        all {
          10.0 dashed Color.RED
        }

        top {
          5.0 dotted Color.GREEN
        }
      }

      checkAllBorderSettings(
        Border(
          5.0,
          Border.BorderStyle.DOTTED,
          Color.GREEN
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        )
      )
    }
  }

  @Test
  fun `right after all overrides all for right Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        all {
          10.0 dashed Color.RED
        }

        right {
          5.0 dotted Color.GREEN
        }
      }

      checkAllBorderSettings(
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          5.0,
          Border.BorderStyle.DOTTED,
          Color.GREEN
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        )
      )
    }
  }

  @Test
  fun `bottom after all overrides all for bottom Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        all {
          10.0 dashed Color.RED
        }

        bottom {
          5.0 dotted Color.GREEN
        }
      }

      checkAllBorderSettings(
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          5.0,
          Border.BorderStyle.DOTTED,
          Color.GREEN
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        )
      )
    }
  }

  @Test
  fun `left after all overrides all for left Border`() {
    with(BorderBox(Border())) {
      checkAllBorderSettings()

      border {
        all {
          10.0 dashed Color.RED
        }

        left  {
          5.0 dotted Color.GREEN
        }
      }

      checkAllBorderSettings(
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          10.0,
          Border.BorderStyle.DASHED,
          Color.RED
        ),
        Border(
          5.0,
          Border.BorderStyle.DOTTED,
          Color.GREEN
        )
      )
    }
  }

  private fun BorderBox.border(style: BorderBox.() -> Unit) = style()

  private fun BorderBox.checkReferences() {
    assertTrue(left !== right)
    assertTrue(left !== top)
    assertTrue(left !== bottom)
    assertTrue(right !== top)
    assertTrue(right !== bottom)
    assertTrue(top !== bottom)
  }

  private fun BorderBox.checkAllBorderSettings(
    topBorder: Border = Border(),
    rightBorder: Border = Border(),
    bottomBorder: Border = Border(),
    leftBorder: Border = Border()
  ) {
    top.checkBorderSettings(topBorder)
    right.checkBorderSettings(rightBorder)
    bottom.checkBorderSettings(bottomBorder)
    left.checkBorderSettings(leftBorder)
  }

  private fun Border.checkBorderSettings(
    border: Border = Border()
  ) {
    checkBorderSettings(border.borderWidth, border.borderStyle, border.borderColor)
  }

  private fun Border.checkBorderSettings(
    borderWidth: Double = 0.0,
    borderStyle: Border.BorderStyle = Border.BorderStyle.NONE,
    borderColor: Color? = Color.BLACK
  ) {
    assertEquals(borderWidth, this.borderWidth)
    assertEquals(borderStyle, this.borderStyle)
    assertEquals(borderColor, this.borderColor)
  }
}