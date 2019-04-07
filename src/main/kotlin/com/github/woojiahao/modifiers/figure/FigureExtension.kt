package com.github.woojiahao.modifiers.figure

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.util.options.MutableDataHolder
import java.io.File

class FigureExtension(private val markdownFile: File) : HtmlRenderer.HtmlRendererExtension {

  override fun rendererOptions(options: MutableDataHolder?) {}

  override fun extend(rendererBuilder: HtmlRenderer.Builder?, rendererType: String?) {
    rendererBuilder?.nodeRendererFactory(FigureFactory(markdownFile))
  }
}