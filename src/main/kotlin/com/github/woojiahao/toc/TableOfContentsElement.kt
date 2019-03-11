package com.github.woojiahao.toc

data class TableOfContentsElement(
  val level: Int,
  val content: String,
  val isTopLevel: Boolean = false
)