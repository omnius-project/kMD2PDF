package com.github.woojiahao.toc

private val invertedHeaderOrder = mapOf(
  1 to 6,
  2 to 5,
  3 to 4,
  4 to 3,
  5 to 2,
  6 to 1
)

fun generateTableOfContents(tableOfContents: List<TableOfContentsElement>, settings: TableOfContentsSettings) =
  tableOfContents
    .joinToString("\n") {
      val level = it.level
      val invertedHeaderValue = invertedHeaderOrder.getValue(level)
      val padding = (level - 1) * 20
      val fontSize = invertedHeaderValue * 4
      val fontWeight = if (level == 1) "bold" else "normal"
      "<h$level style='padding-left: ${padding}px; font-size: ${fontSize}px; font-weight: $fontWeight'>${it.content}</h$level>"
    }
