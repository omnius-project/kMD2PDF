package com.github.woojiahao

import com.github.kittinunf.result.Result
import com.github.woojiahao.modifiers.MediaReplacedElementFactory
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.utility.getFontDirectories
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

class PdfConversionHandler(
  html: String,
  css: String,
  targetLocation: File,
  extra: Map<String, Any> = emptyMap()
) : IConversionHandler<File>(html, css, targetLocation, extra) {

  override fun generateHtml(extra: Map<String, String>) =
    StringBuilder().appendHTML().html {
      head { style { unsafe { raw(css) } } }
      body { unsafe { raw(html) } }
    }.toString()


  override fun convert() =
    try {
      with(ITextRenderer()) {
        val documentProperties = extra["documentProperties"] as DocumentProperties

        val content = htmlToXML(generateHtml())
        val outputLocation by lazy { FileOutputStream(targetLocation) }

        sharedContext.replacedElementFactory = MediaReplacedElementFactory(
          documentProperties,
          sharedContext.replacedElementFactory
        )
        setDocumentFromString(content)
        loadFontDirectories()
        layout()
        createPDF(outputLocation)
        Result.success(targetLocation)
      }
    } catch (e: Exception) {
      Result.error(e)
    }

  private fun ITextRenderer.loadFontDirectories() {
    val fontDirectories = getFontDirectories()
    val hasNoFontDirectory = fontDirectories.none { File(it).exists() }
    // TODO: Use logger for this
    if (hasNoFontDirectory) {
      println("Font folders could not be located on your system, fonts will default")
    }
    fontDirectories.forEach { fontResolver.addFontDirectory(it, false) }
  }
}