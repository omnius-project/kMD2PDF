package com.github.woojiahao.modifiers.figure

data class FigcaptionSettings(
  var isVisible: Boolean = true,
  var prepend: String = "Figure",
  var append: String = "",
  var divider: String = "-"
)