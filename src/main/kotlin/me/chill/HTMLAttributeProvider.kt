package me.chill

import com.itextpdf.text.BaseColor
import org.commonmark.node.Code
import org.commonmark.node.Heading
import org.commonmark.node.Node
import org.commonmark.renderer.html.AttributeProvider

class HTMLAttributeProvider(private val style: PDFStyle) : AttributeProvider {
  override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
    with(node) {
      when (this) {
        is Heading -> {
          attributes["font-family"] = style.headerFontFamily
          attributes["color"] = style.headerFontColor.cssColor()
          attributes["font-size"] = style.matchHeaderSize(level)
        }
        is Code -> {
          attributes["font-family"] = style.codeFontFamily
          attributes["color"] = style.codeFontColor.cssColor()
          attributes["background-color"] = style.codeBackgroundColor.cssColor()
          attributes["font-size"] = style.codeFontSize.toString() + "px"
        }
      }
    }
  }

  private fun BaseColor.cssColor() = "rgb($red, $green, $blue)"
}