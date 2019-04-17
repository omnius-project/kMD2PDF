package com.github.woojiahao.generators

import com.github.woojiahao.modifiers.toc.TableOfContentsElement
import com.github.woojiahao.modifiers.toc.generateTableOfContents
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.elements.document.DocumentArea
import kotlinx.html.TagConsumer
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
    val content = StringBuilder()
      .appendHTML()
      .apply {
        generateTableOfContents()
        generateHeader()
        generateFooter()
        generateBody()
      }

    return content.finalize().toString()
  }

  private fun TagConsumer<StringBuilder>.generateTableOfContents() {
    val tocSettings = documentProperties.tableOfContentsSettings
    if (tocSettings.isVisible) {
      div("table-of-contents") {
        h1 { +"Table of contents" }
        unsafe { raw(generateTableOfContents(tableOfContents, tocSettings)) }
      }
    }
  }

  private fun TagConsumer<StringBuilder>.generateHeader() = generateDocumentArea(documentStyle.header, "header")

  private fun TagConsumer<StringBuilder>.generateFooter() = generateDocumentArea(documentStyle.footer, "footer")

  private fun TagConsumer<StringBuilder>.generateBody() {
    div("content") {
      unsafe { +wrap(documentBody) }
    }
  }

  private fun TagConsumer<StringBuilder>.generateDocumentArea(area: DocumentArea, areaName: String) {
    with(area) {
      div("$areaName-left") { +left.getContents() }

      div("$areaName-center") { +center.getContents() }

      div("$areaName-right") { +right.getContents() }
    }
  }
}