package com.github.woojiahao

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.commonmark.node.BulletList
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.node.Text
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import java.util.*

class TaskListNodeRenderer(context: HtmlNodeRendererContext) : NodeRenderer {

  private val taskItemRegex = Regex("^\\[(| |X|x)?\\] (.*)\$")

  private val html = context.writer

  override fun getNodeTypes(): MutableSet<Class<BulletList>> = Collections.singleton(BulletList::class.java)

  override fun render(node: Node?) {
    val bulletList = node as BulletList

    val list = StringBuilder().appendHTML().ul {
      bulletList.iterateNodeChildren {
        val listItemContent = (it.firstChild.firstChild as Text).literal.trim()

        val isTaskItem = taskItemRegex.containsMatchIn(listItemContent)

        if (isTaskItem) {
          classes = setOf("task-list")

          val match = taskItemRegex.matchEntire(listItemContent)
          match ?: return@iterateNodeChildren

          val taskCompletionStatus = match.groups[1]?.value ?: return@iterateNodeChildren
          val taskDescription = match.groups[2]?.value ?: return@iterateNodeChildren

          val isTaskCompleted = taskCompletionStatus.toLowerCase() == "x"

          li("task-list-item") {
            unsafe {
              val inputString = StringBuilder("<input type=\"checkbox\" disabled=\"disabled\"")
              if (isTaskCompleted) inputString.append(" checked=\"checked\"")
              inputString.append("></input>")
              raw(inputString.toString())
            }

            +taskDescription
          }
        } else {
          li {
            +listItemContent
          }
        }
      }
    }

    html.raw(list.toString())
    html.line()
  }

  private fun Node.iterateNodeChildren(operation: (Node) -> Unit) {
    var listItem = firstChild
    while (listItem != null) {
      val next = listItem.next
      operation(listItem)
      listItem = next
    }
  }
}