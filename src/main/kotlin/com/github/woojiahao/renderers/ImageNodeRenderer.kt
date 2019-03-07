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
    with(node as Image) {
      if (title == null) {
        html.line()
        html.tag(
          "img", mapOf(
            "src" to destination
          )
        )
        html.tag("/img")
        html.line()
      } else {
        html.line()
        html.tag("figure")
        html.line()
        html.tag(
          "img", mapOf(
            "src" to destination,
            "alt" to title
          )
        )
        html.tag("/img")
        html.line()
        html.tag("br")
        html.tag("/br")
        html.tag("figcaption")
        html.raw("<b>Figure:</b> $title")
        html.tag("/figcaption")
        html.line()
        html.tag("/figure")
        html.line()
      }
    }
  }
}