package me.chill.style

import me.chill.style.elements.*

/**
 * Default styling for an exported PDF.
 *
 * Supply a [baseFontSize] to be applied to all elements unless otherwise
 * specified. This [baseFontSize] will influence the scaling of each
 * header.
 *
 * Supply a [baseFontFamily] to be applied to all elements unless otherwise
 * specified. [Code] will change this font to use a monospace font instead.
 */
class PDFStyle(
  private val baseFontSize: Double = 16.0,
  private val baseFontFamily: FontFamily = FontFamily("serif")
) : GenericPDFStyle() {

  override var headerOne = HeaderOne(baseFontSize, baseFontFamily)
  override var headerTwo = HeaderTwo(baseFontSize, baseFontFamily)
  override var headerThree = HeaderThree(baseFontSize, baseFontFamily)
  override var headerFour = HeaderFour(baseFontSize, baseFontFamily)
  override var headerFive = HeaderFive(baseFontSize, baseFontFamily)
  override var headerSix = HeaderSix(baseFontSize, baseFontFamily)
  override var code = Code(baseFontSize, FontFamily("monospace"))
  override var bold = Bold(baseFontSize, baseFontFamily)
  override var paragraph = Paragraph(baseFontSize, baseFontFamily)
  override var link = Link(baseFontSize, baseFontFamily)

  companion object {
    /**
     * Creates a custom PDFStyle object using a custom DSL.
     *
     * Supply a [baseFontSize] to be applied to all element unless otherwise specified.
     * [baseFontSize] will influence the scaling of each header.
     */
    fun createStyle(
      baseFontSize: Double = 16.0,
      baseFontFamily: FontFamily = FontFamily("serif"),
      styleFunction: PDFStyle.() -> Unit
    ) = PDFStyle(baseFontSize, baseFontFamily).apply { styleFunction() }
  }

  fun code(style: Code.() -> Unit) {
    this.code = Code(baseFontSize, FontFamily("monospace")).apply { style() }
  }

  fun bold(style: Bold.() -> Unit) {
    this.bold = Bold(baseFontSize, baseFontFamily).apply { style() }
  }

  fun paragraph(style: Paragraph.() -> Unit) {
    this.paragraph = Paragraph(baseFontSize, baseFontFamily).apply { style() }
  }

  fun link(style: Link.() -> Unit) {
    this.link = Link(baseFontSize, baseFontFamily).apply { style() }
  }

  fun headerOne(style: HeaderOne.() -> Unit) {
    this.headerOne = HeaderOne(baseFontSize).apply { style() }
  }

  fun headerTwo(style: HeaderTwo.() -> Unit) {
    this.headerTwo = HeaderTwo(baseFontSize).apply { style() }
  }

  fun headerThree(style: HeaderThree.() -> Unit) {
    this.headerThree = HeaderThree(baseFontSize).apply { style() }
  }

  fun headerFour(style: HeaderFour.() -> Unit) {
    this.headerFour = HeaderFour(baseFontSize).apply { style() }
  }

  fun headerFive(style: HeaderFive.() -> Unit) {
    this.headerFive = HeaderFive(baseFontSize).apply { style() }
  }

  fun headerSix(style: HeaderSix.() -> Unit) {
    this.headerSix = HeaderSix(baseFontSize).apply { style() }
  }
}
