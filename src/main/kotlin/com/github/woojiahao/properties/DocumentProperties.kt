package com.github.woojiahao.properties

import com.github.woojiahao.style.utility.Box

class DocumentProperties {

  constructor()

  private constructor(
    documentSize: DocumentSize,
    leftMargin: Box<Double>,
    rightMargin: Box<Double>
  ) {
    println(documentSize.size)
    println(leftMargin.toCss())
    println(rightMargin.toCss())
  }

  open class Builder {
    private var documentSize = DocumentSize()
    private var leftMargin = Box(0.0)
    private var rightMargin = Box(0.0)

    fun documentSize(documentSize: DocumentSize): Builder {
      this.documentSize = documentSize
      return this
    }

    fun leftMargin(leftMargin: Box<Double>): Builder {
      this.leftMargin = leftMargin
      return this
    }

    fun rightMargin(rightMargin: Box<Double>): Builder {
      this.rightMargin = rightMargin
      return this
    }

    fun build() = DocumentProperties(documentSize, leftMargin, rightMargin)
  }
}