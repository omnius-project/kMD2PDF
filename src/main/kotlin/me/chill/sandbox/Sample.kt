package me.chill.sandbox

import me.chill.MarkdownDocument
import me.chill.PDFStyle

fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md", CustomStyle())
  println(document.toHTML())
  document.convertToPDF()
}

class CustomStyle : PDFStyle() {
  override val headerFontFamily = "Arial"
  override val headerOneFontSize = 10
  override val codeFontFamily = "Source Code Pro"
}