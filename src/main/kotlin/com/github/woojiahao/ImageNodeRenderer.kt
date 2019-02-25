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
    with(node as Image) {
      html.line()
      html.tag("figure")
      html.line()
      html.tag("img", mapOf(
        "src" to destination,
        "alt" to title
      ))
      html.tag("/img")
      html.line()
      html.tag("figcaption")
      html.tag("p")
      html.text("Figure: ${title}")
      html.tag("/p")
      html.tag("/figcaption")
      html.line()
      html.tag("/figure")
      html.line()
    }
  }
}