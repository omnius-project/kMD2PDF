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
}