package com.github.woojiahao.renderers

import org.commonmark.node.Image
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter
import java.util.*

class ImageNodeRenderer(context: HtmlNodeRendererContext) : NodeRenderer {

  private val html = context.writer

  override fun getNodeTypes(): MutableSet<Class<Image>> = Collections.singleton(Image::class.java)

  override fun render(node: Node?) {
    val image = node as Image
    val destination = image.destination
    val title = image.title

    val imageAttributes = loadImageAttributes(title, destination)

    title?.let { loadImageWithCaption(imageAttributes) } ?: loadImage(imageAttributes)
  }

  private fun loadImageAttributes(title: String?, destination: String?): Map<String, String?> {
    val imageAttributes = mutableMapOf("src" to destination)
    title?.let { imageAttributes["alt"] = it }
    return imageAttributes.toMap()
  }

  private fun loadImage(imageAttributes: Map<String, String?>) {
    with(html) {
      line()
      createTag("img", imageAttributes)
      line()
    }
  }

  private fun loadImageWithCaption(imageAttributes: Map<String, String?>) {
    val caption = imageAttributes["alt"]

    with(html) {
      line()
      createTag("figure") {
        line()
        createTag("img", imageAttributes)
        line()
        createTag("br")
        createTag("figcaption") { text(caption) }
        createTag("br")
        line()
      }
      line()
    }
  }

  private fun createTag(
    name: String,
    attributes: Map<String, String?> = emptyMap(),
    content: HtmlWriter.() -> Unit = { }
  ) {
    with(html) {
      tag(name, attributes)
      content()
      tag("/$name")
    }
  }
}