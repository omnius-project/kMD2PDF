package com.github.woojiahao.style.utility

class FontFamily(vararg fonts: String) {

  enum class BaseFontFamily {
    SERIF, SANS_SERIF, CURSIVE, FANTASY, MONOSPACE;

    fun toCss() = name.toLowerCase().replace("_", "-")
  }

  constructor(fallBackFont: BaseFontFamily, vararg fonts: String)
      : this(*createFontListWithFallbackFont(fallBackFont, fonts))

  constructor(fontFamily: FontFamily) : this(*fontFamily.fontFamily.toTypedArray())

  private val fontFamily = mutableListOf<String>()

  val fonts
    get() = fontFamily.toList()

  init {
    fontFamily.addAll(fonts)
  }

  operator fun String.unaryPlus() = fontFamily.add(this)

  fun clear() = fontFamily.clear()

  fun clone() = FontFamily(this)

  override fun toString() =
    fontFamily.joinToString(", ") {
      if (it.split(" ").size > 1) "'$it'"
      else it
    }

  companion object {
    private fun createFontListWithFallbackFont(
      fallBackFont: BaseFontFamily,
      fonts: Array<out String>
    ): Array<String> {
      val fontList = fonts.toMutableList()
      fontList += fallBackFont.toCss()
      return fontList.toTypedArray()
    }
  }
}