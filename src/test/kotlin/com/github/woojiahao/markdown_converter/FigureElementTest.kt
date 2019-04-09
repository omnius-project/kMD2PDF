package com.github.woojiahao.markdown_converter

import org.junit.Ignore
import org.junit.Test

class FigureElementTest : ElementTest("figure") {
  @Test
  fun `No alt text renders single img`() = assert("NoAltText")

  @Test
  fun `Alt text renders to figure`() = assert("AltText")

  @Test
  fun `Absolute web paths is used as src`() = assert("AbsoluteWebPath")

  @Test
  fun `Local relative path will map to markdown file location and be used as src`() = assert("RelativeLocalPath")

  /**
   * Ignoring this test until absolute file path can be handled via Travis and in the markdown.
   *
   * Running it locally on my Windows machine works.
   *
   * TODO: Figure out how to create absolute file paths in the markdown document
   */
  @Test
  @Ignore
  fun `Local absolute path is used as src`() = assert("AbsoluteLocalPath")
}