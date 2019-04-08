package com.github.woojiahao.properties

import com.github.woojiahao.FigcaptionSettings
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.`in`
import com.github.woojiahao.modifiers.toc.TableOfContentsSettings

class DocumentProperties private constructor(
  val size: DocumentSize,
  val margins: Box<Measurement<Double>>,
  val leftPageMargins: Box<Measurement<Double>>?,
  val rightPageMargins: Box<Measurement<Double>>?,
  val tableOfContentsSettings: TableOfContentsSettings,
  val figcaptionSettings: FigcaptionSettings
) {

  open class Builder {
    private var size = DocumentSize()
    private var margins = Box(0.4.`in`)
    private var leftPageMargins: Box<Measurement<Double>>? = null
    private var rightPageMargins: Box<Measurement<Double>>? = null
    private var tableOfContentsSettings = TableOfContentsSettings()
    private var figcaptionSettings = FigcaptionSettings()

    fun size(size: DocumentSize): Builder {
      this.size = size
      return this
    }

    fun margins(margins: Box<Measurement<Double>>): Builder {
      this.margins = margins
      return this
    }

    fun leftPageMargins(leftPageMargins: Box<Measurement<Double>>): Builder {
      this.leftPageMargins = leftPageMargins
      return this
    }

    fun rightPageMargins(rightPageMargins: Box<Measurement<Double>>): Builder {
      this.rightPageMargins = rightPageMargins
      return this
    }

    fun tableOfContentsSettings(tableOfContentsSettings: TableOfContentsSettings): Builder {
      this.tableOfContentsSettings = tableOfContentsSettings
      return this
    }

    fun figcaptionSettings(figcaptionSettings: FigcaptionSettings): Builder {
      this.figcaptionSettings = figcaptionSettings
      return this
    }

    fun build() = DocumentProperties(
      size,
      margins,
      leftPageMargins,
      rightPageMargins,
      tableOfContentsSettings,
      figcaptionSettings
    )
  }
}