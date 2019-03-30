package com.github.woojiahao.style

import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.px
import org.junit.Test
import kotlin.test.assertEquals

class BoxTest {
  @Test
  fun `Constructor with 4 inputs distribute values in top-right-bottom-left order`() {
    val box = Box(5, 10, 15, 20)
    box.checkBoxDimensions(5, 10, 15, 20)
  }

  @Test
  fun `Constructor with 2 inputs distribute values in top-bottom then left-right order`() {
    val box = Box("foo", "bar")
    box.checkBoxDimensions("foo", "bar", "foo", "bar")
  }

  @Test
  fun `Constructor with 1 input distributes same value to all sides in top-right-bottom-left order`() {
    val box = Box(14.3)
    box.checkBoxDimensions(14.3, 14.3, 14.3, 14.3)
  }

  @Test
  fun `toString without modification returns box dimensions in top-right-bottom-left order untouched`() {
    val box = Box("foo", "bar", "baz", "fuzz")
    val expectedOutput = "foo bar baz fuzz"
    assertEquals(expectedOutput, box.toCss())
  }

  @Test
  fun `toString with modification returns box dimensions in top-right-bottom-left order modified`() {
    val box = Box(13.9, 43.7, 82.1, 9.2)
    val expectedOutput = "13.9px 43.7px 82.1px 9.2px"
    assertEquals(expectedOutput, box.toCss { it.px })
  }

  private fun <T> Box<T>.checkBoxDimensions(top: T, right: T, bottom: T, left: T) {
    assertEquals(top, this.top)
    assertEquals(right, this.right)
    assertEquals(bottom, this.bottom)
    assertEquals(left, this.left)
  }
}