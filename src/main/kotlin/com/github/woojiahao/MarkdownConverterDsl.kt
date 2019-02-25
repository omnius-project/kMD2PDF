package com.github.woojiahao

import com.github.woojiahao.properties.DocumentPropertiesBuilderDsl
import com.github.woojiahao.properties.documentProperties
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.SANS_SERIF

@DslMarker
annotation class MarkdownConverterDsl

@MarkdownConverterDsl
class MarkdownConverterBuilderDsl : MarkdownConverter.Builder()

inline fun markdownConverter(converter: MarkdownConverterBuilderDsl.() -> Unit) =
  with(MarkdownConverterBuilderDsl()) {
    converter()
    build()
  }

inline fun MarkdownConverterBuilderDsl.style(
  baseFontSize: Double = 16.0,
  baseFontFamily: FontFamily = FontFamily(SANS_SERIF),
  style: Style.() -> Unit
) {
  val customStyle = Style.createStyle(baseFontSize, baseFontFamily) { style() }
  this.style(customStyle)
}

inline fun MarkdownConverterBuilderDsl.documentProperties(
  properties: DocumentPropertiesBuilderDsl.() -> Unit
) {
  val documentProperties = with(DocumentPropertiesBuilderDsl()) {
    properties()
    build()
  }
  documentProperties(documentProperties)
}