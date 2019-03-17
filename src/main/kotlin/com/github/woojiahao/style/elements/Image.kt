package com.github.woojiahao.style.elements

class Image : Element("img") {

  val figCaption = FigCaption()

  fun figcaption(style: FigCaption.() -> Unit) = figCaption.style()
}