package com.github.woojiahao.modifiers.figure

import com.vladsch.flexmark.html.renderer.NodeRendererFactory
import com.vladsch.flexmark.util.options.DataHolder
import java.io.File

class FigureFactory(private val markdownFile: File) : NodeRendererFactory {
  override fun create(options: DataHolder?) = FigureNodeRenderer(markdownFile)
}