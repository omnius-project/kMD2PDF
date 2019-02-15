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
          attributes["font-family"] = style.matchHeaderLevel(level) {
            it.fontFamily.joinToString(", ")
          }
          attributes["color"] = style.matchHeaderLevel(level) {
            it.fontColor.cssColor()
          }
          attributes["font-size"] = style.matchHeaderLevel(level) {
            it.fontSize.toString() + "px"
          }
        }
        is Code -> {
          with(style.code) {
            attributes["font-family"] = getFontFamilyString()
            attributes["color"] = fontColor.cssColor()
            attributes["background-color"] = backgroundColor.cssColor()
            attributes["font-size"] = getFontSizeString()

          }
        }
      }
    }
  }

  private fun Color.cssColor() = "rgb($red, $green, $blue)"
}