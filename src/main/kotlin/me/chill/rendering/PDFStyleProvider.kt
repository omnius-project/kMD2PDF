package me.chill.rendering

import me.chill.style.InlineStyleRenderer
import me.chill.style.PDFStyle
import me.chill.style.elements.Element
import me.chill.utility.cssColor
import me.chill.utility.px
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

      is Code -> inlineStyleRenderer.setStyle(style.inlineCode)

      is StrongEmphasis -> inlineStyleRenderer.setStyle(style.bold)

      is Paragraph -> inlineStyleRenderer.setStyle(style.p)

      is Link -> inlineStyleRenderer.setStyle(style.link)

      is BlockQuote -> inlineStyleRenderer.setStyle(style.blockQuote)

      is Image -> inlineStyleRenderer.setStyle(style.img)

      is ListBlock -> {
        val listType = when (node) {
          is BulletList -> style.ul
          is OrderedList -> style.ol
          else -> style.ul
        }

        with(listType) {
          inlineStyleRenderer
            .setStyle(this)
            .attribute("list-style-type", listStyleType.toCss())
            .attribute("list-style-position", listStylePosition.name.toLowerCase())
        }
      }
    }

    attributes["style"] = inlineStyleRenderer.renderStyle()
  }

  private fun InlineStyleRenderer.setStyle(element: Element) =
    with(element) {
      if (padding != null) {
        attribute("padding", padding?.toCss { it.px })
      }
      if (margin != null) {
        attribute("margin", margin?.toCss { it.px })
      }
      attribute("font-size", fontSize.px)
      attribute("font-family", fontFamily)
      attribute("color", fontColor?.cssColor())
      attribute("background-color", backgroundColor?.cssColor())
      attribute("font-weight", fontWeight.name.toLowerCase())
      attribute("text-decoration", textDecoration.toCss())
      attribute("border-top", border.top)
      attribute("border-right", border.right)
      attribute("border-bottom", border.bottom)
      attribute("border-left", border.left)
      attribute("border-radius", borderRadius.toCss { it.px })
    }
}