package me.chill.rendering

import me.chill.style.InlineStyleRenderer
import me.chill.style.PDFStyle
import me.chill.style.elements.Element
import me.chill.utility.cssColor
import me.chill.utility.px
import org.bouncycastle.crypto.tls.ExtensionType
import org.commonmark.node.*
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.HtmlRenderer

/**
 * Adds inline styles to HTML elements as they get rendered by [HtmlRenderer].
 * Styles are read from [style].
 */
class PDFStyleProvider(private val style: PDFStyle) : AttributeProvider {

  override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
    val inlineStyleRenderer = InlineStyleRenderer()

    when (node) {
      is Heading -> inlineStyleRenderer.setStyle(style.matchHeaderLevel(node.level))

      is Code -> {
        with(style.inlineCode) {
          inlineStyleRenderer
            .setStyle(this)
            .attribute("border-radius", borderRadius.px)
            .attribute("padding", padding.px)
        }
      }

      is StrongEmphasis -> inlineStyleRenderer.setStyle(style.bold)

      is Paragraph -> inlineStyleRenderer.setStyle(style.paragraph)

      is Link -> inlineStyleRenderer.setStyle(style.link)
    }

    attributes["style"] = inlineStyleRenderer.renderStyle()
  }

  private fun InlineStyleRenderer.setStyle(element: Element) =
    with(element) {
      attribute("font-size", fontSize.px)
      attribute("font-family", fontFamily)
      attribute("color", fontColor?.cssColor())
      attribute("background-color", backgroundColor?.cssColor())
      attribute("font-weight", fontWeight.name.toLowerCase())
      attribute("text-decoration", textDecoration.toCss())
      attribute("border", border)
    }
}