package com.github.woojiahao.modifiers.figure

import com.github.woojiahao.modifiers.figure.FigureNodeRenderer.DestinationType.*
import com.vladsch.flexmark.ast.Image
import com.vladsch.flexmark.html.CustomNodeRenderer
import com.vladsch.flexmark.html.HtmlWriter
import com.vladsch.flexmark.html.renderer.NodeRenderer
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.html.Attributes
import java.io.File
import java.net.MalformedURLException
import java.net.URI

class FigureNodeRenderer(private val markdownFile: File) : NodeRenderer {

  private enum class DestinationType { WEB, RELATIVE_LOCAL, ABSOLUTE_LOCAL }

  private val urlSeparator = "/"

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

  override fun getNodeRenderingHandlers() =
    hashSetOf<NodeRenderingHandler<out Node>>(
      object : NodeRenderingHandler<Image>(
        Image::class.java,
        CustomNodeRenderer<Image> { image, _, writer ->
          render(image, writer)
        }
      ) {}
    )

  fun render(node: Image, html: HtmlWriter) {
    val destination = node.url.unescape()
    val title = node.title.unescape()

    val isDestinationLocalFile = destination.isLocalFile

    val processedDestination = when (isDestinationLocalFile) {
      RELATIVE_LOCAL -> processLocalFileLocation(destination)
      else -> destination
    }

    val imageAttributes = loadImageAttributes(title, processedDestination, isDestinationLocalFile)

    with(html) {
      title?.let { loadImageWithCaption(imageAttributes) } ?: loadImage(imageAttributes)
    }
  }


  private fun processLocalFileLocation(localFilePath: String): String {
    val localPath = markdownFile
      .parent
      .replace("\\", urlSeparator)
      .split(urlSeparator)
      .toMutableList()

    localFilePath.split(urlSeparator).forEach {
      // The string compared has a tendency to change to "src/main" when the file location is changed,
      // it should be ".."
      if (it == "..") localPath.removeAt(localPath.size - 1)
      else localPath += it
    }

    return localPath.joinToString(urlSeparator)
  }

  private fun loadImageAttributes(title: String?, destination: String?, destinationType: DestinationType) =
    Attributes().apply {
      addValue("src", destination)
      title?.let { addValue("alt", it) }
      addValue("class", when (destinationType) {
        RELATIVE_LOCAL, ABSOLUTE_LOCAL -> "local"
        else -> ""
      })
    }

  private fun HtmlWriter.loadImage(imageAttributes: Attributes) {
    line()
    createTag("img", imageAttributes)
    line()
  }

  private fun HtmlWriter.loadImageWithCaption(imageAttributes: Attributes) {
    val caption = imageAttributes.getValue("alt")

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

  private fun HtmlWriter.createTag(
    name: String,
    attributes: Attributes = Attributes(),
    content: HtmlWriter.() -> Unit = { }
  ) {
    tag(name).apply { attr(attributes) }
    content()
    tag("/$name")
  }
}