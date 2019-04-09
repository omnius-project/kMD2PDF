package com.github.woojiahao.markdown_converter

import org.junit.Test

class FigureElementTest : ElementTest("figure") {
  @Test
  fun `No alt text renders single img`() = assert("NoAltText")

  @Test
  fun `Alt text renders to figure`() = assert("AltText")

  @Test
  fun `Absolute web paths will generate a figure with src set to web path`() = assert("AbsoluteWebPath")

}