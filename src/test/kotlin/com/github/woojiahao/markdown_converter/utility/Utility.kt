package com.github.woojiahao.markdown_converter.utility

import com.github.woojiahao.MarkdownDocument
import com.github.woojiahao.markdownConverter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File
import kotlin.test.assertEquals

private val resourcesDirectory
  get() = File("src/test/resources/markdown-converter")

fun getResource(folder: String, file: String) = File(File(resourcesDirectory, folder), file)

fun setupConverter(markdownFile: File) =
  markdownConverter {
    val markdownDocument = MarkdownDocument(markdownFile)
    document(markdownDocument)
  }

fun assertMarkdown(folder: String, file: String) {
  require(file.indexOf(".") == -1) { "File should not include extensions as they are added within the method" }

  val markdownFileName = "$file.md"
  val htmlFileName = "$file.html"

  val markdownFile = getResource(folder, markdownFileName)
  val htmlFile = getResource(folder, htmlFileName)

  val converter = setupConverter(markdownFile)

  val expectedDocument = parseDocument(htmlFile.readText()).body()
  val actualDocument = parseDocument(converter.generateBody()).getElementsByClass("content").first()

  // Ensure that they both have the same number of children
  assertEquals(expectedDocument.childCount, actualDocument.childCount)

  // Ensure that they both have the same set of elements
  val expectedDocumentBody = expectedDocument.children()
  val actualDocumentBody = actualDocument.children()

  expectedDocumentBody.zip(actualDocumentBody).forEach { compare(it.first, it.second) }
}

private fun compare(ex: Element, ac: Element) {
  assertElementEquals(ex, ac)

  if (ex.childCount != 0) {
    (0 until ex.childCount).forEach {
      compare(ex.child(it), ac.child(it))
    }
  }
}

val Element.childCount
  get() = children().size

fun assertElementEquals(ex: Element, ac: Element) {
  assertEquals(ex.childCount, ac.childCount, "Element child count don't match")
  assertEquals(ex.tagName(), ac.tagName(), "Element tags don't match")
  assertEquals(ex.ownText(), ac.ownText(), "Element text don't match")
  assertEquals(ex.attributes().size(), ac.attributes().size(), "Element attribute size don't match")
  ex.attributes().zip(ac.attributes()).forEach {
    val exAttribute = it.first
    val acAttribute = it.second
    assertEquals(exAttribute.key, acAttribute.key, "Attribute key don't match")
    assertEquals(exAttribute.value, acAttribute.value, "Attribute value don't match")
  }
}

fun parseDocument(html: String): Document =
  Jsoup.parse(html).apply {
    outputSettings().syntax(Document.OutputSettings.Syntax.xml)
    html().replace("&nbsp;", " ")
  }