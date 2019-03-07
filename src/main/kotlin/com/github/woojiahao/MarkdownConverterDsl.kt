package com.github.woojiahao

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Style

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
  settings: Settings = Settings(),
  style: Style.() -> Unit
) {
  this.style(Style.createStyle(settings) { style() })
}

inline fun MarkdownConverterBuilderDsl.documentProperties(
  properties: DocumentProperties.Builder.() -> Unit
) {
  documentProperties(DocumentProperties.Builder().apply { properties() }.build())
}