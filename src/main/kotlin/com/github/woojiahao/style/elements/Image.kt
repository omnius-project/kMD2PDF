package com.github.woojiahao.style.elements

class Image : Element("img") {

  val figcaption = FigCaption()

  fun figcaption(style: FigCaption.() -> Unit) = figcaption.style()
}