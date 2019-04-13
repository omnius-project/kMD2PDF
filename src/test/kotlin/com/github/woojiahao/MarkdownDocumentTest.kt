package com.github.woojiahao

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Test

class MarkdownDocumentTest {

  @get:Rule
  val folder = TemporaryFolder()

  private val userHome = System.getProperty("user.home")

  @Test(expected = IllegalArgumentException::class)
  fun `File path must point to existing file`() {
    MarkdownDocument("$userHome/nonExistent.md")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `File must have markdown extension`() {
    val file = folder.newFile("test.txt")
    MarkdownDocument(file)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `File cannot be folder path`() {
    val subFolder = folder.newFolder("test")
    MarkdownDocument(subFolder)
  }

  @Test
  fun `Valid markdown document will open`() {
    val file = folder.newFile("README.md")
    MarkdownDocument(file)
  }
}