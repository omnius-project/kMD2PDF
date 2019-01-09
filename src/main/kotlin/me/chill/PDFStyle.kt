package me.chill

import com.itextpdf.text.BaseColor
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory

open class PDFStyle {

  // Header preferences
  open val headerFontFamily = FontFactory.HELVETICA
  open val headerFontColor = BaseColor.BLACK
  open val headerOneFontSize = 48
  open val headerTwoFontSize = 39
  open val headerThreeFontSize = 31
  open val headerFourFontSize = 25
  open val headerFiveFontSize = 20
  open val headerSixFontSize = 48

  // Code preferences
  open val codeFontFamily = FontFactory.COURIER
  open val codeFontColor = BaseColor.WHITE
  open val codeBackgroundColor = BaseColor.BLACK
  open val codeFontSize = 16

  fun matchHeaderSize(headerLevel: Int) =
    when (headerLevel) {
      1 -> headerOneFontSize
      2 -> headerTwoFontSize
      3 -> headerThreeFontSize
      4 -> headerFourFontSize
      5 -> headerFiveFontSize
      6 -> headerSixFontSize
      else -> headerOneFontSize
    }.toString() + "px"
}
