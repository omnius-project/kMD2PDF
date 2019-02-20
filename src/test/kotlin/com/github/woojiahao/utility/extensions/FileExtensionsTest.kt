package com.github.woojiahao.utility.extensions

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FileExtensionsTest {

  @get:Rule
  val folder = TemporaryFolder()

  @Test
  fun `isFileType returns false when used with a folder`() {
    val tempFolder = folder.newFolder("Test")
    val matchResult = tempFolder.isFileType("pdf")
    assertFalse(matchResult)
  }

  @Test
  fun `isFileType returns false if single file extension do not match`() {
    val file = folder.newFile("test.txt")
    val matchResult = file.isFileType("pdf")
    assertFalse(matchResult)
  }

  @Test
  fun `isFileType returns false if multiple file extensions do not match`() {
    val file = folder.newFile("test.txt")
    val matchResult = file.isFileType("pdf", "png", "jpg")
    assertFalse(matchResult)
  }

  @Test
  fun `isFileType returns false if target file extensions is empty`() {
    val file = folder.newFile("test.txt")
    val matchResult = file.isFileType()
    assertFalse(matchResult)
  }

  @Test
  fun `isFileType returns true if single file extension match`() {
    val file = folder.newFile("test.txt")
    val matchResult = file.isFileType("txt")
    assertTrue(matchResult)
  }

  @Test
  fun `isFileType returns true if multiple file extensions match`() {
    val file = folder.newFile("test.txt")
    val matchResult = file.isFileType("png", "md", "txt")
    assertTrue(matchResult)
  }
}