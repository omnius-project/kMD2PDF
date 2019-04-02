package com.github.woojiahao.modifiers.renderers

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
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
    node as BulletList

    val bulletListContent = node.getListItemText()

    val list = createList(bulletListContent)

    html.raw(list)
    html.line()
  }

  private fun createList(listItemContents: List<ListItemContent>): String {
    val list = StringBuilder().appendHTML().ul {
      for (listItemContent in listItemContents) {
        val isTaskItem = listItemContent.isTaskListItem

        if (!isTaskItem) {
          li {
            listItemContent.contents.forEach { paragraph ->
              p { +paragraph }
            }
          }
          continue
        }

        classes = setOf("task-list")

        val match = taskItemRegex.matchEntire(listItemContent.contents[0])
        match ?: continue

        val taskCompletionStatus = match.groups[1]?.value ?: continue
        val taskDescription = match.groups[2]?.value ?: continue

        val isTaskCompleted = taskCompletionStatus.toLowerCase() == "x"

        listItemContent.contents.forEachIndexed { index, paragraph ->
          li("task-list-item") {
            p {
              if (index == 0) {
                unsafe {
                  val checkboxAttributes = loadCheckboxAttributes(isTaskCompleted)
                  raw(createCheckboxString(checkboxAttributes))
                }
                +taskDescription
              } else {
                +paragraph
              }
            }
          }
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

  private fun BulletList.getListItemText(): List<ListItemContent> {
    val contents = mutableListOf<ListItemContent>()
    iterateNodeChildren {
      val listItemContent = ListItemContent()
      it.iterateNodeChildren { paragraph ->
        val paragraphContent = mutableListOf<String>()
        paragraph.iterateNodeChildren { text ->
          if (text is Text)
            paragraphContent += text.literal
        }
        listItemContent.addParagraph(paragraphContent.joinToString(""))
      }
      contents += listItemContent
    }
    return contents.toList()
  }

  private fun Node.iterateNodeChildren(operation: (Node) -> Unit) {
    var listItem = firstChild
    while (listItem != null) {
      val next = listItem.next
      operation(listItem)
      listItem = next
    }
  }

  private class ListItemContent {
    private val taskItemRegex = Regex("^\\[(| |X|x)?\\] (.*)\$")

    private val paragraphs = mutableListOf<String>()

    val contents
      get() = paragraphs.toList()

    fun addParagraph(paragraph: String) {
      paragraphs += paragraph
    }

    val isTaskListItem
      get() =
        if (contents.isEmpty()) false
        else taskItemRegex.matches(contents[0])
  }
}