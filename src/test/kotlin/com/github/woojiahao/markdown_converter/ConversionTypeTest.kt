package com.github.woojiahao.markdown_converter

import com.github.woojiahao.MarkdownConverter
import com.github.woojiahao.MarkdownConverter.ConversionTarget.HTML
import com.github.woojiahao.MarkdownDocument
import com.github.woojiahao.markdownConverter
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * Tests for the ConversionType method in the [MarkdownConverter.Builder] to ensure proper validations are made.
 * ConversionType will be abbreviated to **CType**.
 */
class ConversionTypeTest {

  @get:Rule
  val folder = TemporaryFolder()

  @Test(expected = IllegalStateException::class)
  fun `If CType is HTML, target location must be folder`() {
    assertTargetLocation(createTempLocation("README.pdf"))
  }

  @Test(expected = IllegalStateException::class)
  fun `If CType is PDF, target location must end with pdf`() {
    assertTargetLocation(createTempLocation("README"))
    assertTargetLocation(createTempLocation("README.html"), "README2")
  }

  private fun createTempLocation(fileName: String) = folder.newFile(fileName).absolutePath

  private fun assertTargetLocation(
    targetLocation: String,
    documentName: String = "README",
    contents: String = "# Hello world"
  ) = markdownConverter {
    document(createMarkdownDocument(documentName, contents))
    conversionTarget(HTML)
    targetLocation(targetLocation)
  }

  private fun createMarkdownDocument(name: String = "README", contents: String = "# Hello world"): MarkdownDocument {
    val file = folder.newFile("$name.md").also {
      it.writeText(contents)
    }

    return MarkdownDocument(file)
  }
}