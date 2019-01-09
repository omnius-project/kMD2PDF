package me.chill

import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import java.io.File

class MarkdownDocument(private val filePath: String) {

  private val markdownParser = Parser.builder().build()
  private val htmlRenderer = HtmlRenderer.builder().build()
  private var document: Node

  init {
    with(File(filePath)) {
      kotlin.require(exists()) { "File path ($filePath) must exist" }
      kotlin.require(extension == "md") { "File must be a markdown document" }

      document = markdownParser.parse(readText())
    }
  }

  fun toHTML() = htmlRenderer.render(document).trim()
}