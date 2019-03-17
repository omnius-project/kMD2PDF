package com.github.woojiahao.properties

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Settings.Theme.DARK
import com.github.woojiahao.style.Settings.Theme.LIGHT
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.css.CssAttributes
import com.github.woojiahao.style.elements.document.DocumentText
import com.github.woojiahao.utility.c
import com.github.woojiahao.utility.cssColor
import com.github.woojiahao.utility.cssSelector
import java.awt.Color

class PagePropertiesManager(documentProperties: DocumentProperties, style: Style) {

  private val size = documentProperties.size
  private val margins = documentProperties.margins
  private val leftPageMargins = documentProperties.leftPageMargins
  private val rightPageMargins = documentProperties.rightPageMargins

  private val parentPageSelector = cssSelector("@page") {
    attributes {
      "size" to size.size
      "margin" to margins?.toCss { it.toString() }
      "background-color" to when (Settings.theme) {
        DARK -> c("212121")?.cssColor()
        LIGHT -> Color.WHITE.cssColor()
      }
    }

    nested {
      this += cssSelector("@top-left") {
        attributes { loadDocumentAreaAttributes(style.header.left, "header-left") }
      }

      this += cssSelector("@top-center") {
        attributes { loadDocumentAreaAttributes(style.header.center, "header-center") }
      }

      this += cssSelector("@top-right") {
        attributes { loadDocumentAreaAttributes(style.header.right, "header-right") }
      }

      this += cssSelector("@bottom-left") {
        attributes { loadDocumentAreaAttributes(style.footer.left, "footer-left") }
      }

      this += cssSelector("@bottom-center") {
        attributes { loadDocumentAreaAttributes(style.footer.center, "footer-center") }
      }

      this += cssSelector("@bottom-right") {
        attributes { loadDocumentAreaAttributes(style.footer.right, "footer-right") }
      }
    }
  }

  private val leftPageSelector = cssSelector("@page :left") {
    attributes {
      "margin" to leftPageMargins?.toCss { it.toString() }
    }
  }

  private val rightPageSelector = cssSelector("@page :right") {
    attributes {
      "margin" to rightPageMargins?.toCss { it.toString() }
    }
  }

  private fun CssAttributes.loadDocumentAreaAttributes(contentArea: DocumentText, elementContent: String) {
    with(contentArea) {
      "content" to generateAreaContent(contentArea, elementContent)
      if (hasPageNumber) append(globalCss.attributes)
    }
  }

  private fun generateAreaContent(contentArea: DocumentText, elementContent: String) =
    with(contentArea) {
      if (hasPageNumber) "\"$pageNumberPrepend\"counter(page)\"$pageNumberAppend\""
      else "element($elementContent)"
    }

  fun toCss() = listOf(
    parentPageSelector,
    leftPageSelector,
    rightPageSelector
  ).joinToString("\n\n") { it.toCss() }
}