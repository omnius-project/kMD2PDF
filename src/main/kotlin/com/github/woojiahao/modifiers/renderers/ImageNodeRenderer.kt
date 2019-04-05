package com.github.woojiahao.modifiers.renderers

import com.github.woojiahao.modifiers.renderers.ImageNodeRenderer.DestinationType.*
import org.commonmark.node.Image
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter
import java.io.File
import java.net.MalformedURLException
import java.net.URI
import java.util.*

class ImageNodeRenderer(private val document: File, context: HtmlNodeRendererContext) : NodeRenderer {

  private enum class DestinationType { WEB, RELATIVE_LOCAL, ABSOLUTE_LOCAL }

  private val urlSeparator = "/"

  private val html = context.writer

  private val String?.isLocalFile: DestinationType
    get() {
      this ?: return WEB

      with(URI(replace("\\", urlSeparator))) {
        return try {
          toURL()
          WEB
        } catch (e: Exception) {
          when (e) {
            is MalformedURLException -> ABSOLUTE_LOCAL
            is IllegalArgumentException -> RELATIVE_LOCAL
            else -> throw e
          }
        }
      }
    }

  override fun getNodeTypes(): MutableSet<Class<Image>> = Collections.singleton(Image::class.java)

  override fun render(node: Node?) {
    node as Image
    val destination = node.destination
    val title = node.title

    val isDestinationLocalFile = destination.isLocalFile

    val processedDestination = when (isDestinationLocalFile) {
      RELATIVE_LOCAL -> processLocalFileLocation(destination)
      else -> destination
    }

    val imageAttributes = loadImageAttributes(title, processedDestination, isDestinationLocalFile)

    title?.let { loadImageWithCaption(imageAttributes) } ?: loadImage(imageAttributes)
  }

  private fun processLocalFileLocation(localFilePath: String): String {
    val localPath = document
      .parent
      .replace("\\", urlSeparator)
      .split(urlSeparator)
      .toMutableList()

    localFilePath.split(urlSeparator).forEach {
      if (it == "..") localPath.removeAt(localPath.size - 1)
      else localPath += it
    }

    return localPath.joinToString(urlSeparator)
  }

  private fun loadImageAttributes(
    title: String?,
    destination: String?,
    destinationType: DestinationType
  ): Map<String, String?> {
    val imageAttributes = mutableMapOf("src" to destination)
    title?.let { imageAttributes["alt"] = it }
    imageAttributes["class"] = when (destinationType) {
      RELATIVE_LOCAL, ABSOLUTE_LOCAL -> "local"
      else -> ""
    }
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
        createTag("figcaption") {
          text(caption)
        }
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