package me.chill.sandbox

import me.chill.MarkdownDocument

fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF()
}