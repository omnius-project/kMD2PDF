package com.github.woojiahao.properties

import com.github.woojiahao.properties.DocumentOrientation.PORTRAIT

class DocumentSize {

  var size = "auto"
    private set

  constructor(pageSize: PageSize, orientation: DocumentOrientation = PORTRAIT) {
    size = "${pageSize.sizeName} ${orientation.name.toLowerCase()}"
  }

  constructor(width: Double, height: Double) {
    size = "${width}in ${height}in"
  }

  constructor(auto: Boolean = true) {
    if (auto) size = "auto"
  }
}