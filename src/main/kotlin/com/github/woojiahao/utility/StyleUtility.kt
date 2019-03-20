package com.github.woojiahao.utility

import java.awt.Color

/**
 * Returns a matching [Color] based on the given [hexCode].
 */
fun c(hexCode: String): Color? {
  val convertedHexCode = hexCode.replace("#", "").toLowerCase()

  require(convertedHexCode.length == 6) { "Invalid hex code length (${convertedHexCode.length})" }

  convertedHexCode.forEach {
    require(it.isLetterOrDigit()) { "Invalid character ($it) in hex code" }
    if (it.isLetter()) {
      require(it in 'a'..'f') { "Invalid character ($it)" }
    }
  }

  return Color.decode("#$convertedHexCode")
}

/**
 * Converts a [Color] to the rgb format for CSS.
 */
fun Color.cssColor() = "rgb($red, $green, $blue)"