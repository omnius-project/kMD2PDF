package me.chill

class FontFamily(vararg fonts: String) {
  private val fontFamily = mutableListOf<String>()

  init {
    fontFamily.addAll(fonts)
  }

  operator fun String.unaryPlus() = fontFamily.add(this)

  fun emptyFontFamily() = fontFamily.clear()

  fun getFontFamilyString() = fontFamily.joinToString(", ") { "\"$it\"" }
}