package com.github.woojiahao.generators

import com.github.woojiahao.modifiers.toc.TableOfContentsElement
import com.github.woojiahao.modifiers.toc.generateTableOfContents
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.style.Style
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.stream.appendHTML
import kotlinx.html.unsafe

class HtmlGenerator(
  private val documentStyle: Style,
  private val documentBody: String,
  private val documentProperties: DocumentProperties,
  private val tableOfContents: List<TableOfContentsElement>
) : AbstractContentGenerator() {
  override fun generate(): String {
    with(StringBuilder().appendHTML()) {
      val tocSettings = documentProperties.tableOfContentsSettings
      if (tocSettings.isVisible) {
        div("table-of-contents") {
          h1 { +"Table of contents" }
          unsafe { raw(generateTableOfContents(tableOfContents, tocSettings)) }
        }
      }

      with(documentStyle.header) {
        div("header-left") { +left.getContents() }

        div("header-center") { +center.getContents() }

        div("header-right") { +right.getContents() }
      }

      with(documentStyle.footer) {
        div("footer-left") { +left.getContents() }

        div("footer-center") { +center.getContents() }

        div("footer-right") { +right.getContents() }
      }

      div("content") {
        unsafe { +wrap(documentBody) }
      }

      return finalize().toString()
    }
  }
}