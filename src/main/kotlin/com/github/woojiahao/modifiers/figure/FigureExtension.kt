package com.github.woojiahao.modifiers.figure

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.util.data.MutableDataHolder
import java.io.File

class FigureExtension(private val markdownFile: File) : HtmlRenderer.HtmlRendererExtension {

  companion object {
    fun create(markdownFile: File) = FigureExtension(markdownFile)
  }

  override fun rendererOptions(options: MutableDataHolder?) {}

  override fun extend(rendererBuilder: HtmlRenderer.Builder?, rendererType: String?) {
    with(rendererBuilder) {
      this ?: return
      nodeRendererFactory(FigureRendererFactory(markdownFile))
    }
  }
}