package com.github.woojiahao.style.elements.document

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element

class DocumentText(elementName: String, settings: Settings) : Element(elementName, settings) {

  private val contents = mutableListOf<String>()
  var hasPageNumber = false
    private set
  var pageNumberPrepend = ""
    private set
  var pageNumberAppend = ""
    private set

  operator fun String.unaryPlus() = contents.add(this)

  fun pageNumber(prepend: String = "", append: String = "") {
    hasPageNumber = true
    pageNumberPrepend = prepend
    pageNumberAppend = append
  }

  fun getContents() = contents.joinToString("\n")
}