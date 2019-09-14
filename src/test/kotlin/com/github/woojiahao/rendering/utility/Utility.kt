package com.github.woojiahao.rendering.utility

import com.github.woojiahao.MarkdownDocument
import com.github.woojiahao.markdownConverter
import org.jsoup.nodes.Element
import java.io.File
import kotlin.test.assertEquals

private val resourcesDirectory
  get() = File("src/test/resources/rendering")

fun getResource(folder: String, file: String, extension: String? = null): File {
  val fileName = extension?.let { "$file.$it" } ?: file
  return File(File(resourcesDirectory, folder), fileName)
}

fun setupConverter(markdownFile: File) =
  markdownConverter {
    val markdownDocument = MarkdownDocument(markdownFile)
    document(markdownDocument)
  }

fun assertMarkdown(folder: String, file: String) {
  require(file.indexOf(".") == -1) { "File should not include extensions as they are added within the method" }

  val markdownFile = getResource(folder, file, "md")
  val htmlFile = getResource(folder, file, "html")

  val converter = setupConverter(markdownFile)

  val expectedDocument = parseDocument(htmlFile.readText()).body()
  val actualDocument = parseDocument(converter.body).getElementsByClass("content").first()

  // Ensure that they both have the same number of children
  assertEquals(
    expectedDocument.childCount,
    actualDocument.childCount,
    generateChildCountAssertFalseMessage(expectedDocument, actualDocument, "Document child count don't match")
  )

  // Ensure that they both have the same set of elements
  val expectedDocumentBody = expectedDocument.children()
  val actualDocumentBody = actualDocument.children()

  expectedDocumentBody.zip(actualDocumentBody).forEach {
    compare(it.first, it.second)
  }
}

private fun compare(ex: Element, ac: Element) {
  assertElementEquals(ex, ac)

  if (ex.childCount != 0) {
    (0 until ex.childCount).forEach {
      compare(ex.child(it), ac.child(it))
    }
  }
}

private fun assertElementEquals(ex: Element, ac: Element) {
  assertEquals(
    ex.childCount,
    ac.childCount,
    generateChildCountAssertFalseMessage(ex, ac, "Element child count don't match")
  )
  assertEquals(ex.tagName(), ac.tagName(), "Element tags don't match")
  assertEquals(ex.ownText(), ac.ownText(), "Element text don't match")
  assertEquals(ex.attributes().size(), ac.attributes().size(), "Element attribute size don't match")

  val sortedExAttributes = ex.attributes().sortedBy { it.key }
  val sortedAcAttributes = ac.attributes().sortedBy { it.key }
  sortedExAttributes.zip(sortedAcAttributes).forEach {
    val exAttribute = it.first
    val acAttribute = it.second
    assertEquals(exAttribute.key, acAttribute.key, "Attribute key don't match")
    assertEquals(exAttribute.value, acAttribute.value, "Attribute value don't match")
  }
}

private fun generateChildCountAssertFalseMessage(ex: Element, ac: Element, prepend: String = "") =
  mutableListOf<String>().apply {
    if (prepend.isNotEmpty()) this += prepend
    this += "Expected: ${ex.childrenNames.joinToString(", ")}"
    this += "Actual: ${ac.childrenNames.joinToString(", ")}"
  }.joinToString("\n")