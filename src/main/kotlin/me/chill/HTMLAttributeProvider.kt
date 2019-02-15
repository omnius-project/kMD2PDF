package me.chill

import org.commonmark.node.*
import org.commonmark.renderer.html.AttributeProvider
import java.awt.Color

class HTMLAttributeProvider(private val style: PDFStyle) : AttributeProvider {

  override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
    val inlineStyleRenderer = InlineStyleRenderer()
    with(node) {
      when (this) {
        is Heading -> {
          inlineStyleRenderer
            .attribute("font-family", style.matchHeaderLevel(level) { it.fontFamily.joinToString(", ") })
            .attribute("color", style.matchHeaderLevel(level) { it.fontColor.cssColor() })
            .attribute("font-size", style.matchHeaderLevel(level) { it.fontSize.toString() + "px" })

        }

        is Code, is FencedCodeBlock -> {
          with(style.code) {
            inlineStyleRenderer
              .attribute("font-family", getFontFamilyString())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
              .attribute("border-radius", "5px")
              .attribute("padding", "3px")
          }
        }

        is StrongEmphasis -> {
          with(style.bold) {
            inlineStyleRenderer
              .attribute("font-family", getFontFamilyString())
              .attribute("font-weight", fontWeight.name.toLowerCase())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
          }
        }

        is Paragraph -> {
          with (style.paragraph) {
            inlineStyleRenderer
              .attribute("font-family", getFontFamilyString())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
          }
        }

        is Link -> {
          with (style.link) {
            println(getFontFamilyString())
            inlineStyleRenderer
              .attribute("font-family", getFontFamilyString())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
              .attribute("text-decoration", textDecoration.name.toLowerCase())
          }
        }
      }

      attributes["style"] = inlineStyleRenderer.renderStyle()
    }
  }

  private fun Color.cssColor() = "rgb($red, $green, $blue)"
}