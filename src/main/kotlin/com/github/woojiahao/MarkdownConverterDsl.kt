package com.github.woojiahao

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.MONOSPACE
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
  fontSize: Double = 16.0,
  font: FontFamily = FontFamily(SANS_SERIF),
  monospaceFont: FontFamily = FontFamily(MONOSPACE),
  style: Style.() -> Unit
) {
  this.style(Style.createStyle(fontSize, font, monospaceFont) { style() })
}

inline fun MarkdownConverterBuilderDsl.documentProperties(
  properties: DocumentProperties.Builder.() -> Unit
) {
  documentProperties(DocumentProperties.Builder().apply { properties() }.build())
}