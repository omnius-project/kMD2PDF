package me.chill.sandbox

import me.chill.MarkdownDocument
import me.chill.PDFStyle
import me.chill.createStyle
import me.chill.elements.Bold
import me.chill.elements.Code
import me.chill.elements.Link
import me.chill.elements.Paragraph
import me.chill.elements.headers.HeaderOne
import java.awt.Color

fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md", CustomStyle())
  document.convertToPDF()
}

fun createDSLStyle() {
  createStyle {
    code {
      fontSize = 16.0
      fontFamily = listOf("Hack")
    }
  }
}

class CustomStyle : PDFStyle() {
  override var code = object : Code() {
    override var fontSize = 32.0
    override var fontFamily = listOf("Hack")
  }

  override var headerOne = object : HeaderOne() {
    override var fontColor = Color.RED
  }

  override var bold = object : Bold() {
    override var fontColor = Color.PINK
  }

  override var paragraph = object : Paragraph() {
    override var fontFamily = listOf("Raleway", "Lato", "Roboto")
  }

  override var link = object : Link() {
    override var fontFamily = listOf("Monaco")
  }
}