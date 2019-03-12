package com.github.woojiahao.renderers

import org.commonmark.node.Image
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
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
      tag("img", imageAttributes)
      tag("/img")
      line()
    }
  }

  private fun loadImageWithCaption(imageAttributes: Map<String, String?>) {
    val caption = imageAttributes["alt"]

    with(html) {
      line()
      tag("figure")
      line()
      tag("img", imageAttributes)
      tag("/img")
      line()
      tag("br")
      tag("/br")
      tag("figcaption")
      raw("<b>Figure:</b> $caption")
      tag("/figcaption")
      line()
      tag("/figure")
      line()
    }
  }
}