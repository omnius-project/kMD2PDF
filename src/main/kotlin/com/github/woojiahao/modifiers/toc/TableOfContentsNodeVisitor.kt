package com.github.woojiahao.modifiers.toc

import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.util.ast.NodeVisitor
import com.vladsch.flexmark.util.ast.VisitHandler


class TableOfContentsNodeVisitor(
  val visitor: TableOfContentsVisitor = TableOfContentsVisitor(TableOfContentsSettings())
) : NodeVisitor(
  VisitHandler<Heading>(
    Heading::class.java,
    visitor
  )
)