package com.github.woojiahao.toc

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Heading
import org.commonmark.node.Node
import org.commonmark.node.Text

class TableOfContentsVisitor(settings: TableOfContentsSettings) : AbstractVisitor() {

  val tableOfContents = TableOfContents(settings)

  override fun visit(heading: Heading?) {
    heading ?: return

    heading.iterateNodeChildren {
      val headingContent = (it as Text).literal
      tableOfContents.addLevel(
        TableOfContents.Level(
          heading.level,
          headingContent
        )
      )
    }
  }

  private fun Node.iterateNodeChildren(operation: (Node) -> Unit) {
    var currentChild = firstChild
    while (currentChild != null) {
      val next = currentChild.next
      operation(currentChild)
      currentChild = next
    }
  }
}