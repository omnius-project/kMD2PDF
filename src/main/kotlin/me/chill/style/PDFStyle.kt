package me.chill.style

import me.chill.style.elements.*

/**
 * Default styling for an exported PDF.
 *
 * Supply a [baseFontSize] to be applied to all elements unless otherwise
 * specified. This [baseFontSize] will influence the scaling of each
 * header.
 */
class PDFStyle(private val baseFontSize: Double = 16.0) : GenericPDFStyle() {

  override var headerOne = HeaderOne(baseFontSize)
  override var headerTwo = HeaderTwo(baseFontSize)
  override var headerThree = HeaderThree(baseFontSize)
  override var headerFour = HeaderFour(baseFontSize)
  override var headerFive = HeaderFive(baseFontSize)
  override var headerSix = HeaderSix(baseFontSize)
  override var code = Code(baseFontSize)
  override var bold = Bold(baseFontSize)
  override var paragraph = Paragraph(baseFontSize)
  override var link = Link(baseFontSize)

  companion object {
    /**
     * Creates a custom PDFStyle object using a custom DSL.
     *
     * Supply a [baseFontSize] to be applied to all element unless otherwise specified.
     * [baseFontSize] will influence the scaling of each header.
     */
    fun createStyle(baseFontSize: Double = 16.0, styleFunction: PDFStyle.() -> Unit): PDFStyle {
      val style = PDFStyle(baseFontSize)
      style.styleFunction()
      return style
    }
  }

  fun code(style: Code.() -> Unit) {
    val code = Code(baseFontSize)
    code.style()
    this.code = code
  }

  fun bold(style: Bold.() -> Unit) {
    val bold = Bold(baseFontSize)
    bold.style()
    this.bold = bold
  }

  fun paragraph(style: Paragraph.() -> Unit) {
    val paragraph = Paragraph(baseFontSize)
    paragraph.style()
    this.paragraph = paragraph
  }

  fun link(style: Link.() -> Unit) {
    val link = Link(baseFontSize)
    link.style()
    this.link = link
  }

  fun headerOne(style: HeaderOne.() -> Unit) {
    val header = HeaderOne(baseFontSize)
    header.style()
    this.headerOne = header
  }

  fun headerTwo(style: HeaderTwo.() -> Unit) {
    val header = HeaderTwo(baseFontSize)
    header.style()
    this.headerTwo = header
  }

  fun headerThree(style: HeaderThree.() -> Unit) {
    val header = HeaderThree(baseFontSize)
    header.style()
    this.headerThree = header
  }

  fun headerFour(style: HeaderFour.() -> Unit) {
    val header = HeaderFour(baseFontSize)
    header.style()
    this.headerFour = header
  }

  fun headerFive(style: HeaderFive.() -> Unit) {
    val header = HeaderFive(baseFontSize)
    header.style()
    this.headerFive = header
  }

  fun headerSix(style: HeaderSix.() -> Unit) {
    val header = HeaderSix(baseFontSize)
    header.style()
    this.headerSix = header
  }
}
