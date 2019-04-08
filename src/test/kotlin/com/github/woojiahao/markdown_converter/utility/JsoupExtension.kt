package com.github.woojiahao.markdown_converter.utility

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import kotlin.test.assertEquals

val Element.childCount
  get() = children().size

val Element.firstChild
  get() = child(0)

val Element.lastChild
  get() = child(childCount - 1)

val Element.isBlankParagraph
  get() = `is`("p") && text().isEmpty()

val Element.isSurroundedByBlankParagraphs
  get() = firstChild.isBlankParagraph && lastChild.isBlankParagraph

fun assertElementEquals(ex: Element, ac: Element) {
  assertEquals(ex.childCount, ac.childCount)
  assertEquals(ex.tagName(), ac.tagName())
  assertEquals(ex.ownText(), ac.ownText())
  assertEquals(ex.attributes().size(), ac.attributes().size())
  ex.attributes().zip(ac.attributes()).forEach {
    val exAttribute = it.first
    val acAttribute = it.second
    assertEquals(exAttribute.key, acAttribute.key)
    assertEquals(exAttribute.value, acAttribute.value)
  }
}

fun parseDocument(html: String): Document =
  Jsoup.parse(html).apply {
    outputSettings().syntax(Document.OutputSettings.Syntax.xml)
    html().replace("&nbsp;", " ")
  }