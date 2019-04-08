package com.github.woojiahao.modifiers.toc

data class TableOfContentsElement(
  val level: Int,
  val content: String,
  val isTopLevel: Boolean = false
)