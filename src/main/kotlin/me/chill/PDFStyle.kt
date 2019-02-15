package me.chill

import me.chill.elements.Bold
import me.chill.elements.Code
import me.chill.elements.Element
import me.chill.elements.Paragraph
import me.chill.elements.headers.*

/**
 * CSS styling for the PDF
 */
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
