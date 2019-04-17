package com.github.woojiahao.generators

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.style.Style

class CssGenerator(
  private val documentStyle: Style,
  private val pagePropertiesManager: PagePropertiesManager,
  private val documentProperties: DocumentProperties
) : AbstractContentGenerator() {
  override fun generate(): String {
    val css = StringBuilder()
    css += wrap(documentStyle.getStyles())
    css += wrap(pagePropertiesManager.toCss())
    css += wrap(".table-of-contents") {
      attributes {
        "page-break-after" to "always"
      }
    }

    // Fig caption configuration
    with(documentProperties.figcaptionSettings) {
      val figcaptionNumberLabel = "figures"

      if (isVisible) {
        css += wrap("body") {
          attributes {
            "counter-reset" to figcaptionNumberLabel
          }
        }

        css += wrap("figure") {
          attributes {
            "counter-increment" to figcaptionNumberLabel
          }
        }

        css += wrap("figure figcaption:before") {
          attributes {
            "content" to "'$prepend ' counter($figcaptionNumberLabel) ' $divider '"
          }
        }

        css += wrap("figure figcaption:after") {
          attributes {
            "content" to " '$append'"
          }
        }
      }
    }

    return css.toString()  }
}