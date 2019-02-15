package me.chill.style

import me.chill.style.elements.*

/**
 * Default styling for an exported PDF.
 *
 * Supply a [baseFontSize] to be applied to all elements unless otherwise
 * specified. This [baseFontSize] will influence the scaling of each
 * header.
 */
open class PDFStyle(private val baseFontSize: Double = 16.0) {

  open var headerOne = HeaderOne(baseFontSize)
  open var headerTwo = HeaderTwo(baseFontSize)
  open var headerThree = HeaderThree(baseFontSize)
  open var headerFour = HeaderFour(baseFontSize)
  open var headerFive = HeaderFive(baseFontSize)
  open var headerSix = HeaderSix(baseFontSize)
  open var code = Code(baseFontSize)
  open var bold = Bold(baseFontSize)
  open var paragraph = Paragraph(baseFontSize)
  open var link = Link(baseFontSize)

  companion object {
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

  fun <T> matchHeaderLevel(headerLevel: Int, operation: (Element) -> T) =
    when (headerLevel) {
      1 -> operation(headerOne)
      2 -> operation(headerTwo)
      3 -> operation(headerThree)
      4 -> operation(headerFour)
      5 -> operation(headerFive)
      6 -> operation(headerSix)
      else -> operation(headerOne)
    }
}
