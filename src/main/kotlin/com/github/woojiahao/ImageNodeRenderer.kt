package com.github.woojiahao

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
    val imageAttributes = mapOf(
      "src" to image.destination,
      "alt" to image.title
    )
    html.line()
    html.tag("figure")
    html.tag("img", imageAttributes)
    html.tag("/img")
    html.tag("figcaption")
    html.text(image.title)
    html.tag("/figcaption")
    html.tag("/figure")
    html.line()
  }
}