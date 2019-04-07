package com.github.woojiahao.toc

import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.ast.Text
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.ast.Visitor


class TableOfContentsVisitor(private val settings: TableOfContentsSettings) : Visitor<Heading> {
  private val headers = mutableListOf<TableOfContentsElement>()

  fun getTableOfContents(): List<TableOfContentsElement> {
    val highestLevel = headers
      .topLevelHeaders()
      .sortedBy { it.level }
      .firstOrNull() ?: return emptyList()

    val firstHighestLevel = headers.indexOfFirst { it.level == highestLevel.level }

    return headers
      .subList(firstHighestLevel, headers.size)
      .filter { it.level <= settings.nestedLevel }
  }

  override fun visit(heading: Heading?) {
    heading ?: return

    heading.iterateNodeChildren {
      val content = (it as Text).chars.unescape()
      val level = heading.level

      with(headers) {
        val isTopLevelHeader = isEmpty() || level <= topLevelHeaders().last().level
        val header = TableOfContentsElement(level, content, isTopLevelHeader)

        this += header
      }
    }
  }

  private fun List<TableOfContentsElement>.groupHeaders(): List<List<TableOfContentsElement>> {
    val topLevelIndices = topLevelHeaders().map { indexOf(it) }
    return topLevelIndices
      .mapIndexed { index, topLevelIndex ->
        val endIndex =
          if (index == topLevelIndices.lastIndex) size
          else topLevelIndices[index + 1]
        subList(topLevelIndex, endIndex)
      }.toList()
  }

  private fun List<TableOfContentsElement>.topLevelHeaders() = filter { it.isTopLevel }

  private fun Node.iterateNodeChildren(operation: (Node) -> Unit) {
    var currentChild = firstChild
    while (currentChild != null) {
      val next = currentChild.next
      operation(currentChild)
      currentChild = next
    }
  }
}