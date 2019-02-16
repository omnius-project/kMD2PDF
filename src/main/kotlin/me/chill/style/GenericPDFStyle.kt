package me.chill.style

import me.chill.style.elements.*

open class GenericPDFStyle {
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