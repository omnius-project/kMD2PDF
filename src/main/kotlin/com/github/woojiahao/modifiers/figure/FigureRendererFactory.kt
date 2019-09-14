package com.github.woojiahao.modifiers.figure

import com.vladsch.flexmark.html.renderer.NodeRendererFactory
import com.vladsch.flexmark.util.data.DataHolder
import java.io.File

class FigureRendererFactory(private val markdownFile: File) : NodeRendererFactory {
  override fun apply(options: DataHolder?) = FigureNodeRenderer(markdownFile)
}