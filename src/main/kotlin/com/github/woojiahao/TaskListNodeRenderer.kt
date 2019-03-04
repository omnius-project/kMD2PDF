package com.github.woojiahao

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

    html.tag("ul")
    html.line()
    populateList(bulletList)
    html.tag("/ul")
    html.line()
  }

  private fun populateList(list: BulletList) {
    var listItem = list.firstChild
    while (listItem != null) {
      val next = listItem.next
      val listItemContent = ((listItem.firstChild as Paragraph).firstChild as Text).literal.trim()

      val isTaskItem = taskItemRegex.containsMatchIn(listItemContent)

      if (isTaskItem) {
        with(taskItemRegex.matchEntire(listItemContent)) {
          this ?: return

          val taskCompletionStatus = groups[1]?.value ?: return
          val taskDescription = groups[2]?.value ?: return

          val isTaskCompleted = taskCompletionStatus.toLowerCase() == "x"

          val checkboxSettings = mutableMapOf(
            "type" to "checkbox",
            "disabled" to "disabled"
          )

          if (isTaskCompleted) checkboxSettings["checked"] = "checked"

          html.tag("li", mapOf("class" to "task-list-item"))

          html.tag("input", checkboxSettings)
          html.tag("/input")

          html.text(taskDescription)
        }
      } else {
        html.tag("li")
        html.text(listItemContent)
      }

      html.tag("/li")
      html.line()

      listItem = next
    }
  }
}