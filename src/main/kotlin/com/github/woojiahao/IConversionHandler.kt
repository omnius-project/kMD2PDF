package com.github.woojiahao

import com.github.kittinunf.result.Result
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File

abstract class IConversionHandler<T : Any>(
  protected val html: String,
  protected val css: String,
  protected val targetLocation: File,
  protected val extra: Map<String, Any> = emptyMap()
) {
  abstract fun generateHtml(extra: Map<String, String> = emptyMap()): String
  abstract fun convert(): Result<T, Exception>

  protected fun htmlToXML(html: String) =
    Jsoup
      .parse(html)
      .apply { outputSettings().syntax(Document.OutputSettings.Syntax.xml) }
      .let { it.html().replace("&nbsp;", " ") }
}