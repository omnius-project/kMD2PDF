package com.github.woojiahao.utility

import java.awt.Color

/**
 * Returns a matching [Color] based on the given [hexCode].
 *
 * CSS color shortcuts are supported as well:
 *
 * - c("FA")      => #FAFAFA
 * - c("FB1")     => #FB1FB1
 * - c("FAF6B1")  => #FAF6B1
 */
fun c(hexCode: String): Color? {
  val convertedHexCode = hexCode.replace("#", "").toLowerCase()
  val convertedHexCodeLength = convertedHexCode.length

  val allowedLength = listOf(2, 3, 6)

  require(convertedHexCodeLength in allowedLength) {
    "Invalid hex code length (${convertedHexCode.length}), accepted length include 2, 3 and 6"
  }

  val repetitions = 6 / convertedHexCodeLength
  val finalHexCode = convertedHexCode.repeat(repetitions)

  finalHexCode.forEach {
    require(it.isLetterOrDigit()) { "Invalid character ($it) in hex code" }

    if (it.isLetter()) {
      require(it in 'a'..'f') { "Invalid character ($it)" }
    }
  }

  return Color.decode("#$finalHexCode")
}

/**
 * Converts a [Color] to the rgb format for CSS.
 */
fun Color.cssColor() = "rgb($red, $green, $blue)"