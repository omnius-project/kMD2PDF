package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.FontFamily

class Image(fontSize: Double, fontFamily: FontFamily) : Element("img", fontSize, fontFamily) {

  val figCaption = FigCaption(fontSize, fontFamily)

  fun figcaption(style: FigCaption.() -> Unit) = figCaption.style()
}