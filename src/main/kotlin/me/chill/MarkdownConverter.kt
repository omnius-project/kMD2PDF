package me.chill

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import me.chill.style.AbstractStyle
import me.chill.utility.getFontDirectories
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.renderer.html.HtmlRenderer
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

/**
 * Converts a [markdownDocument] to a PDF, applying the supplied [style] to the PDF.
 */
class MarkdownConverter(
  private val markdownDocument: MarkdownDocument,
  private val style: AbstractStyle
) {

  private val extensions = listOf(
    TablesExtension.create(),
    StrikethroughExtension.create()
  )
  private val htmlRenderer = HtmlRenderer
    .builder()
    .extensions(extensions)
    .build()

  /**
   * Converts the [markdownDocument] into HTML for the PDF to render.
   * [style] is rendered along with the HTML as inline styles.
   */
  fun toHTML() = StringBuilder()
    .appendHTML()
    .html {
      head {
        style {
          unsafe {
            +"\n${extractStyle()}\n"
          }
        }
      }
      body {
        unsafe {
          +htmlRenderer.render(markdownDocument.parsedDocument).trim()
        }
      }
    }.toString()

  /**
   * Converts the given [markdownDocument] to a PDF, saving it at the location
   * specified by [targetFile].
   *
   * Optionally pass [onComplete] and [onError] to be invoked when the file is
   * converted or when it encounters an error.
   */
  fun createPDF(
    targetFile: File,
    onComplete: ((File) -> Unit)? = null,
    onError: ((Exception) -> Unit)? = null
  ) {
    println(toHTML())

    with(ITextRenderer()) {
      setDocumentFromString(toHTML())
      loadFontDirectories()
      layout()
      try {
        createPDF(FileOutputStream(targetFile))
        onComplete?.invoke(targetFile)
      } catch (e: Exception) {
        onError?.invoke(e)
      }
    }
  }

  private fun extractStyle(): String {
    val standardElementCSS = style.getElements().joinToString("\n\n") { it.toCss() }
    val customCSSSelectorsCSS = style
      .customCSSSelectors
      .map {
        val selector = it.key
        val attributes = it.value.toCss()
        StringBuilder("$selector {\n")
          .append(attributes)
          .append("\n}")
          .toString()
      }
      .joinToString("\n\n")
    return StringBuilder()
      .append(standardElementCSS)
      .append("\n\n")
      .append(customCSSSelectorsCSS)
      .toString()
  }

  /**
   * Loads all available font directories into an [ITextRenderer] to be used with
   * the styles.
   *
   * If no font directories are located, a warning is made to the user, selected
   * fonts will not render.
   */
  private fun ITextRenderer.loadFontDirectories() {
    val fontDirectories = getFontDirectories()
    val hasNoFontDirectory = fontDirectories.none { File(it).exists() }
    if (hasNoFontDirectory) {
      println("Font folders could not be located on your system, fonts will default")
    }
    fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
  }
}