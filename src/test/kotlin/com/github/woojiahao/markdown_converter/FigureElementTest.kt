package com.github.woojiahao.markdown_converter

import com.github.woojiahao.markdown_converter.utility.assertMarkdown
import org.junit.Test

class FigureElementTest {
  companion object {
    private const val FIGURE_RESOURCE_NAME = "figure"
  }

  @Test
  fun `Absolute web paths will generate a figure with src set to web path`() {
    assertMarkdown(FIGURE_RESOURCE_NAME, "AbsoluteWebPath")
  }

  @Test
  fun `No alt text renders single img`() {
    assertMarkdown(FIGURE_RESOURCE_NAME, "NoAltText")
  }
}