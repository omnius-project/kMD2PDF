package com.github.woojiahao.markdown_converter.utility

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

val Element.childCount
  get() = children().size

val Element.childrenNames
  get() = children().map { it.tagName() }

fun parseDocument(html: String): Document =
  Jsoup.parse(html).apply {
    outputSettings().syntax(Document.OutputSettings.Syntax.xml)
    html().replace("&nbsp;", " ")
  }