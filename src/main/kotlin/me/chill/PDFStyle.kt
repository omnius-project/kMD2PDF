package me.chill

import me.chill.elements.*
import me.chill.elements.headers.*

/**
 * CSS styling for the PDF
 */
// TODO: Convert to use DSL format
open class PDFStyle {

  open val headerOne = HeaderOne()
  open val headerTwo = HeaderTwo()
  open val headerThree = HeaderThree()
  open val headerFour = HeaderFour()
  open val headerFive = HeaderFive()
  open val headerSix = HeaderSix()
  open val code = Code()
  open val bold = Bold()
  open val paragraph = Paragraph()
  open val link = Link()

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
