package com.github.woojiahao

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class MarkdownDocumentTest {

  @get:Rule
  val folder = TemporaryFolder()

  private val userHome = System.getProperty("user.home")

  @Test(expected = IllegalArgumentException::class)
  fun `IllegalArgumentException thrown if file path does not exist`() {
    MarkdownDocument("$userHome/nonExistent.md")
  }

  @Test(expected = IllegalArgumentException::class)
  fun `IllegalArgumentException thrown if the file is not markdown`() {
    val file = folder.newFile("test.txt")
    MarkdownDocument(file)
  }

  @Test(expected = java.lang.IllegalArgumentException::class)
  fun `IllegalArgumentException thrown if folder is given`() {
    val subFolder = folder.newFolder("test")
    MarkdownDocument(subFolder)
  }

  @Test
  fun `Valid markdown document will open`() {
    val file = folder.newFile("README.md")
    MarkdownDocument(file)
  }

  @Test
  fun `Default markdown should render to default PDF`() {
    val file = generateDefaultMarkdownFile()
    val markdownDocument = MarkdownDocument(file)
//    document.toPDF()
  }

  private fun generateDefaultMarkdownFile() =
    folder
      .newFile("README.md")
      .apply {
        writeText(
          """
          ## Hello world
          This is a **test**!

          ### Sub-heading
          *Hello* world
          """.trimIndent()
        )
      }
}