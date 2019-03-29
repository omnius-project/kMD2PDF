package com.github.woojiahao.renderers

import kotlinx.html.classes
import kotlinx.html.li
import kotlinx.html.stream.appendHTML
import kotlinx.html.ul
import kotlinx.html.unsafe
import org.commonmark.node.BulletList
import org.commonmark.node.Node
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

    val bulletListContent = bulletList.getListItemText()

    val list = createList(bulletListContent)

    html.raw(list)
    html.line()
  }

  private fun createList(listItemContents: List<String>): String {
    val list = StringBuilder().appendHTML().ul {
      for (listItemContent in listItemContents) {
        val isTaskItem = listItemContent.isTaskListItem()

        if (!isTaskItem) {
          li { +listItemContent }
          continue
        }

        classes = setOf("task-list")

        val match = taskItemRegex.matchEntire(listItemContent)
        match ?: continue

        val taskCompletionStatus = match.groups[1]?.value ?: continue
        val taskDescription = match.groups[2]?.value ?: continue

        val isTaskCompleted = taskCompletionStatus.toLowerCase() == "x"

        li("task-list-item") {
          unsafe {
            val checkboxAttributes = loadCheckboxAttributes(isTaskCompleted)
            raw(createCheckboxString(checkboxAttributes))
          }

          +taskDescription
        }
      }
    }

    return list.toString()
  }

  private fun createCheckboxString(checkboxAttributes: Map<String, String>): String {
    val checkboxAttributesString = checkboxAttributes
      .entries
      .joinToString(" ") { "${it.key}=\"${it.value}\"" }
    return "<input $checkboxAttributesString></input>"
  }

  private fun loadCheckboxAttributes(isTaskCompleted: Boolean): Map<String, String> {
    val checkboxAttributes = mutableMapOf("type" to "checkbox", "disabled" to "disabled")
    if (isTaskCompleted) checkboxAttributes["checked"] = "checked"
    return checkboxAttributes.toMap()
  }

  private fun String.isTaskListItem() = taskItemRegex.containsMatchIn(this)

  private fun BulletList.getListItemText(): List<String> {
    val listItemText = mutableListOf<String>()
    iterateNodeChildren {
      val listItemContent = (it.firstChild.firstChild as Text).literal.trim()
      listItemText += listItemContent
    }
    return listItemText.toList()
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