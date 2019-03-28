package com.github.woojiahao.properties

import com.github.woojiahao.properties.DocumentOrientation.PORTRAIT
import com.github.woojiahao.properties.PageSize.A4
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.px

class DocumentSize {

  var size = A4.sizeName
    private set
  var width = 0.0.px
    private set
  var height = 0.0.px
  private set

  constructor(pageSize: PageSize = A4, orientation: DocumentOrientation = PORTRAIT) {
    size = "${pageSize.sizeName} ${orientation.name.toLowerCase()}"
    width = pageSize.width
    height = pageSize.height
  }

  constructor(width: Measurement<Double>, height: Measurement<Double>) {
    size = "$width $height"
    this.width = width
    this.height = height
  }
}