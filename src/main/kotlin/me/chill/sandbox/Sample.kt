package me.chill.sandbox

import me.chill.MarkdownDocument
import me.chill.PDFStyle
import me.chill.elements.Bold
import me.chill.elements.Code
import me.chill.elements.headers.HeaderOne
import java.awt.Color

fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md", CustomStyle())
  document.convertToPDF()
}

class CustomStyle : PDFStyle() {
  override val code = object : Code() {
    override val fontSize = 32.0
    override val fontFamily = listOf("Hack")
  }

  override val headerOne = object : HeaderOne() {
    override val fontColor = Color.RED
  }

  override val bold = object : Bold() {
    override val fontColor = Color.GREEN
  }
}