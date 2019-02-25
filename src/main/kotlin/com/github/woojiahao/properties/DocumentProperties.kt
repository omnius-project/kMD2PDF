package com.github.woojiahao.properties

class DocumentProperties {

  constructor()

  private constructor(documentSize: DocumentSize) {
    println(documentSize.size)
  }

  open class Builder {
    private var documentSize = DocumentSize()

    fun documentSize(documentSize: DocumentSize): Builder {
      this.documentSize = documentSize
      return this
    }

    fun build() = DocumentProperties(documentSize)
  }
}