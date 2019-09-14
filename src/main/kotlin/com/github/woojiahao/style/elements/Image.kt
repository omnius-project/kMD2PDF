package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings

class Image(settings: Settings) : Element("img", settings) {

  val figcaption = FigCaption(settings)

  fun figcaption(style: FigCaption.() -> Unit) = figcaption.style()
}