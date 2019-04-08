package com.github.woojiahao.modifiers.figure

import com.github.woojiahao.modifiers.figure.FigureNodeRenderer.DestinationType.*
import com.vladsch.flexmark.ast.Image
import com.vladsch.flexmark.html.CustomNodeRenderer
import com.vladsch.flexmark.html.HtmlWriter
import com.vladsch.flexmark.html.renderer.NodeRenderer
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler
import com.vladsch.flexmark.util.ast.Node
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.File
import java.net.MalformedURLException
import java.net.URI

class FigureNodeRenderer(private val markdownFile: File) : NodeRenderer {

  private enum class DestinationType { WEB, RELATIVE_LOCAL, ABSOLUTE_LOCAL }

  private val urlSeparator = "/"

  private val String?.destinationType: DestinationType
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
        CustomNodeRenderer<Image> { image, _, writer -> render(image, writer) }
      ) {}
    )

  fun render(node: Image, html: HtmlWriter) {
    val destination = node.url.unescape()
    val title = node.title.unescape()

    val destinationType = destination.destinationType

    val processedDestination = when (destinationType) {
      RELATIVE_LOCAL -> processLocalFileLocation(destination)
      else -> destination
    }

    val imageNode = title?.let { loadImageWithCaption(title, processedDestination, destinationType) }
        ?: loadImage(title, processedDestination, destinationType)

    html.raw(imageNode)
    html.line()
  }

  private fun loadImage(alt: String?, src: String, destinationType: DestinationType) =
    createImageTag(alt, src, destinationType)

  private fun loadImageWithCaption(alt: String, src: String, destinationType: DestinationType) =
    StringBuilder().appendHTML().figure {
      unsafe {
        +createImageTag(alt, src, destinationType)
      }
      br { }
      figcaption {
        +alt
      }
      br { }
    }.toString()

  private fun createImageTag(alt: String?, src: String, destinationType: DestinationType) =
    StringBuilder().appendHTML().img {
      this.src = src
      if (alt != null) this.alt = alt
      classes = setOf(when (destinationType) {
        RELATIVE_LOCAL, ABSOLUTE_LOCAL -> "local"
        else -> ""
      })
    }.toString()

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
}