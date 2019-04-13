package com.github.woojiahao.markdown_converter

import com.github.woojiahao.MarkdownDocument
import com.github.woojiahao.markdownConverter
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class MarkdownConverterTest {

  @get:Rule
  val folder = TemporaryFolder()

  @Test(expected = IllegalStateException::class)
  fun `IllegalStateException thrown if MarkdownDocument is not supplied`() {
    markdownConverter {}
  }

  @Test
  fun `MarkdownDocument must be supplied`() {
    val markdownFile = folder.newFile("README.md").also {
      it.writeText("# Hello")
    }
    val markdownDocument = MarkdownDocument(markdownFile)
    markdownConverter {
      document(markdownDocument)
    }
  }
}