package com.github.woojiahao.generators

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.style.Style

class CssGenerator(
  private val documentStyle: Style,
  private val pagePropertiesManager: PagePropertiesManager,
  private val documentProperties: DocumentProperties
) : AbstractContentGenerator() {

  // TODO: Inline this function
  override fun generate(): String {
    val css = StringBuilder().also {
      it += generateStyle()
      it += generatePageProperties()
      it += generateTableOfContents()
      it += generateFigures()
    }

    return css.toString()
  }

  private fun generateStyle() = wrap(documentStyle.getStyles())

  private fun generatePageProperties() = wrap(pagePropertiesManager.toCss())

  private fun generateTableOfContents() =
    wrap(".table-of-contents") {
      attributes {
        "page-break-after" to "always"
      }
    }

  private fun generateFigures(): String {
    with(documentProperties.figcaptionSettings) {
      if (!isVisible) return ""

      val figureAttributes = mutableListOf<String>()
      val figcaptionNumberLabel = "figures"

      figureAttributes += wrap("body") {
        attributes {
          "counter-reset" to figcaptionNumberLabel
        }
      }

      figureAttributes += wrap("figure") {
        attributes {
          "counter-increment" to figcaptionNumberLabel
        }
      }

      figureAttributes += wrap("figure figcaption:before") {
        attributes {
          "content" to "'$prepend ' counter($figcaptionNumberLabel) ' $divider '"
        }
      }

      figureAttributes += wrap("figure figcaption:after") {
        attributes {
          "content" to " '$append'"
        }
      }
      return figureAttributes.joinToString("")
    }
  }
}