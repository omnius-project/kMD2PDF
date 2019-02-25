package com.github.woojiahao.properties

import com.github.woojiahao.style.utility.Box

/**
 * [size], [margins], [leftPageMargins], [rightPageMargins] are all measured in inches
 */
class DocumentProperties private constructor(
  val size: DocumentSize,
  val margins: Box<Double>?,
  val leftPageMargins: Box<Double>?,
  val rightPageMargins: Box<Double>?
) {

  class Builder {
    private var size = DocumentSize()
    private var margins: Box<Double>? = null
    private var leftPageMargins: Box<Double>? = null
    private var rightPageMargins: Box<Double>? = null

    fun size(size: DocumentSize): Builder {
      this.size = size
      return this
    }

    fun margins(margins: Box<Double>): Builder {
      this.margins = margins
      return this
    }

    fun leftPageMargins(leftPageMargins: Box<Double>): Builder {
      this.leftPageMargins = leftPageMargins
      return this
    }

    fun rightPageMargins(rightPageMargins: Box<Double>): Builder {
      this.rightPageMargins = rightPageMargins
      return this
    }

    fun build() = DocumentProperties(size, margins, leftPageMargins, rightPageMargins)
  }
}