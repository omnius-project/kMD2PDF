package me.chill.sandbox

import me.chill.MarkdownDocument
import me.chill.PDFStyle
import me.chill.elements.Code

fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md", CustomStyle())
  println(document.toHTML())
  document.convertToPDF()
}

class CustomStyle : PDFStyle() {
  override val code = object : Code() {
    override val fontSize = 32.0
  }
}