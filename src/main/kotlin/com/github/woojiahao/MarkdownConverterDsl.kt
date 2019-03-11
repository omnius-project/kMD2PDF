package com.github.woojiahao

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.Style
import com.github.woojiahao.toc.TableOfContentsSettings

@DslMarker
annotation class MarkdownConverterDsl

@MarkdownConverterDsl
class MarkdownConverterBuilderDsl : MarkdownConverter.Builder()

@DslMarker
annotation class DocumentPropertiesDsl

@DocumentPropertiesDsl
class DocumentPropertiesBuilderDsl : DocumentProperties.Builder()

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
  properties: DocumentPropertiesBuilderDsl.() -> Unit
) = with(DocumentPropertiesBuilderDsl()) {
  documentProperties(apply { properties() }.build())
}

inline fun DocumentPropertiesBuilderDsl.tableOfContents(
  properties: TableOfContentsSettings.() -> Unit
) {
  tableOfContentsSettings(TableOfContentsSettings().apply { properties() })
}