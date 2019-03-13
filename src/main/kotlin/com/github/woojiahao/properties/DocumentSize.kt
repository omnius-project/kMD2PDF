package com.github.woojiahao.properties

import com.github.woojiahao.properties.DocumentOrientation.PORTRAIT
import com.github.woojiahao.style.utility.Measurement

class DocumentSize {

  var size = "auto"
    private set

  constructor(pageSize: PageSize, orientation: DocumentOrientation = PORTRAIT) {
    size = "${pageSize.sizeName} ${orientation.name.toLowerCase()}"
  }

  constructor(width: Measurement<Double>, height: Measurement<Double>) {
    size = "$width $height"
  }

  constructor(auto: Boolean = true) {
    if (auto) size = "auto"
  }
}