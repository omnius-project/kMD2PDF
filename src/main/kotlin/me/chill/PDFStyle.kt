package me.chill

import me.chill.elements.*
import me.chill.elements.headers.*

/**
 * CSS styling for the PDF
 */
open class PDFStyle {

  open var headerOne = HeaderOne()
  open var headerTwo = HeaderTwo()
  open var headerThree = HeaderThree()
  open var headerFour = HeaderFour()
  open var headerFive = HeaderFive()
  open var headerSix = HeaderSix()
  open var code = Code()
  open var bold = Bold()
  open var paragraph = Paragraph()
  open var link = Link()

  companion object {
    fun createStyle(styleFunction: PDFStyle.() -> Unit): PDFStyle {
      val style = PDFStyle()
      style.styleFunction()
      return style
    }
  }

  fun code(style: Code.() -> Unit) {
    val code = Code()
    code.style()
    this.code = code
  }

  fun bold(style: Bold.() -> Unit) {
    val bold = Bold()
    bold.style()
    this.bold = bold
  }

  fun paragraph(style: Paragraph.() -> Unit) {
    val paragraph = Paragraph()
    paragraph.style()
    this.paragraph = paragraph
  }

  fun link(style: Link.() -> Unit) {
    val link = Link()
    link.style()
    this.link = link
  }

  fun headerOne(style: HeaderOne.() -> Unit) {
    val header = HeaderOne()
    header.style()
    this.headerOne = header
  }

  fun headerTwo(style: HeaderTwo.() -> Unit) {
    val header = HeaderTwo()
    header.style()
    this.headerTwo = header
  }

  fun headerThree(style: HeaderThree.() -> Unit) {
    val header = HeaderThree()
    header.style()
    this.headerThree = header
  }

  fun headerFour(style: HeaderFour.() -> Unit) {
    val header = HeaderFour()
    header.style()
    this.headerFour = header
  }

  fun headerFive(style: HeaderFive.() -> Unit) {
    val header = HeaderFive()
    header.style()
    this.headerFive = header
  }

  fun headerSix(style: HeaderSix.() -> Unit) {
    val header = HeaderSix()
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
