package me.chill.style

import me.chill.style.elements.*

abstract class GenericPDFStyle {
  abstract var headerOne: HeaderOne
  abstract var headerTwo: HeaderTwo
  abstract var headerThree: HeaderThree
  abstract var headerFour: HeaderFour
  abstract var headerFive: HeaderFive
  abstract var headerSix: HeaderSix
  abstract var code: Code
  abstract var bold: Bold
  abstract var paragraph: Paragraph
  abstract var link: Link
}