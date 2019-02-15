package me.chill

import org.commonmark.node.Code
import org.commonmark.node.Heading
import org.commonmark.node.Node
import org.commonmark.renderer.html.AttributeProvider
import java.awt.Color

class HTMLAttributeProvider(private val style: PDFStyle) : AttributeProvider {

  override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
    with(node) {
      when (this) {
        is Heading -> {
          val headerStyle = InlineStyleRenderer()
            .attribute("font-family", style.matchHeaderLevel(level) { it.fontFamily.joinToString(", ") })
            .attribute("color", style.matchHeaderLevel(level) { it.fontColor.cssColor() })
            .attribute("font-size", style.matchHeaderLevel(level) { it.fontSize.toString() + "px" })
          println(headerStyle.renderStyle())
          attributes["style"] = headerStyle.renderStyle()
          println(attributes["style"])
        }
        is Code -> {
          with(style.code) {
            val codeStyle = InlineStyleRenderer()
              .attribute("font-family", getFontFamilyString())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor.cssColor())
              .attribute("font-size", getFontSizeString())
            println(codeStyle.renderStyle())
            attributes["style"] = codeStyle.renderStyle()
            println(attributes["style"])
          }
        }
      }
    }
  }

  private fun Color.cssColor() = "rgb($red, $green, $blue)"
}