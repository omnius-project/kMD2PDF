package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings

class Image(settings: Settings) : Element("img", settings) {

  val figCaption = FigCaption(settings)

  fun figcaption(style: FigCaption.() -> Unit) = figCaption.style()
}