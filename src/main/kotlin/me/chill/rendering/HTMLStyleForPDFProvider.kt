package me.chill.rendering

import me.chill.style.GenericPDFStyle
import me.chill.style.PDFStyle
import me.chill.style.InlineStyleRenderer
import me.chill.utility.px
import org.commonmark.node.*
import org.commonmark.renderer.html.AttributeProvider
import java.awt.Color

class HTMLStyleForPDFProvider(private val style: GenericPDFStyle) : AttributeProvider {

  override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
    val inlineStyleRenderer = InlineStyleRenderer()
    with(node) {
      when (this) {
        // TODO: Shift into separate methods
        is Heading -> {
          inlineStyleRenderer
            .attribute("font-family", style.matchHeaderLevel(level) { it.fontFamily.toString() })
            .attribute("color", style.matchHeaderLevel(level) { it.fontColor.cssColor() })
            .attribute("font-size", style.matchHeaderLevel(level) { it.fontSize.px })

        }

        is Code, is FencedCodeBlock -> {
          with(style.code) {
            inlineStyleRenderer
              .attribute("font-family", fontFamily.toString())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
              .attribute("border-radius", 5.px)
              .attribute("padding", 3.px)
          }
        }

        is StrongEmphasis -> {
          with(style.bold) {
            inlineStyleRenderer
              .attribute("font-family", fontFamily.toString())
              .attribute("font-weight", fontWeight.name.toLowerCase())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
          }
        }

        is Paragraph -> {
          with(style.paragraph) {
            inlineStyleRenderer
              .attribute("font-family", fontFamily.toString())
              .attribute("color", fontColor.cssColor())
              .attribute("background-color", backgroundColor?.cssColor())
              .attribute("font-size", getFontSizeString())
          }
        }

        is Link -> {
          with(style.link) {
            inlineStyleRenderer
              .attribute("font-family", fontFamily.toString())
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