package me.chill

import me.chill.elements.*
import me.chill.elements.headers.*

/**
 * CSS styling for the PDF
 */
// TODO: Convert to use DSL format
fun createStyle(styleFunction: PDFStyle.() -> Unit): PDFStyle {
  val style = PDFStyle()
  style.styleFunction()
  return style
}

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

  }

  fun link(style: Link.() -> Unit) {

  }

  fun headerOne(style: HeaderOne.() -> Unit) {

  }

  fun headerTwo(style: HeaderTwo.() -> Unit) {

  }

  fun headerThree(style: HeaderTwo.() -> Unit) {

  }

  fun headerFour(style: HeaderTwo.() -> Unit) {

  }

  fun headerFive(style: HeaderTwo.() -> Unit) {

  }

  fun headerSix(style: HeaderTwo.() -> Unit) {

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
