package me.chill

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Test
import kotlin.test.assertEquals

class MarkdownDocumentTest {

  @get:Rule
  val folder = TemporaryFolder()

  @Test(expected = IllegalArgumentException::class)
  fun `IllegalArgumentException thrown if file path does not exist`() {
    MarkdownDocument("C:/Users/Chill/Desktop/nonExistent.md")
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
    val file = folder.newFile("readme.md")
    MarkdownDocument(file)
  }

  @Test
  fun `Default markdown should render to HTML`() {
    val file = generateDefaultMarkdownFile()

    val expectedOutput = """
    <h2>Hello world</h2>
    <p>This is a <strong>test</strong>!</p>
    <h3>Sub-heading</h3>
    <p><em>Hello</em> world</p>
    """.trimIndent()

    val markdownDocument = MarkdownDocument(file)
    assertEquals(expectedOutput, markdownDocument.toHTML())
  }

  @Test
  fun `Default markdown should render to default PDF`() {
    val file = generateDefaultMarkdownFile()
    val markdownDocument = MarkdownDocument(file)
    markdownDocument.convertToPDF("C:/Users/Chill/Desktop/temporary.pdf")
  }

  private fun generateDefaultMarkdownFile() =
    folder
      .newFile("readme.md")
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