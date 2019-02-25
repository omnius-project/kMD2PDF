package com.github.woojiahao.properties

@DslMarker
annotation class DocumentPropertiesDsl

@DocumentPropertiesDsl
class DocumentPropertiesBuilderDsl : DocumentProperties.Builder()

inline fun documentProperties(properties: DocumentPropertiesBuilderDsl.() -> Unit) =
  with(DocumentPropertiesBuilderDsl()) {
    properties()
    build()
  }