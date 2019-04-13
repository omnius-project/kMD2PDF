package com.github.woojiahao

import com.github.kittinunf.result.Result
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.File

class HtmlConversionHandler(
  html: String,
  css: String,
  targetLocation: File,
  extra: Map<String, Any> = emptyMap()
) : IConversionHandler<File>(html, css, targetLocation, extra) {
  private val cssPathKey = "cssPath"

  override fun generateHtml(extra: Map<String, String>) =
    StringBuilder().appendHTML().html {
      val cssPath = extra[cssPathKey]
      head { link(cssPath, "stylesheet", "text/css") }
      body { unsafe { raw(html) } }
    }.toString()

  override fun convert() =
    try {
      val documentName = targetLocation.nameWithoutExtension

      val createParentDirectory = targetLocation.mkdir()
      if (!createParentDirectory)
        Result.error(IllegalStateException("Parent directory couldn't be created, potential conflicting file"))

      val cssFile = File(targetLocation, "$documentName.css").also {
        it.writeText(css)
      }
      val htmlFile = File(targetLocation, "$documentName.html").also {
        val html = htmlToXML(generateHtml(mapOf(cssPathKey to cssFile.absolutePath)))
        it.writeText(html)
      }

      Result.of(targetLocation)
    } catch (e: Exception) {
      Result.error(e)
    }
}