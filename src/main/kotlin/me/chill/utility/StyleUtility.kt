package me.chill.utility

import java.awt.Color

private val hexColorRegex = Regex("#|([0-9A-Fa-f].)")

/**
 * Returns a number in pixel format.
 */
val <T : Number> T.px
  get() = "${this}px"

/**
 * Returns a matching [Color] based on the given [hexCode].
 */
fun c(hexCode: String) =
  with(hexColorRegex.find(hexCode)) {
    require(this != null) { "Hex code not found" }
    require(value.length == 8) { "Hex code ($value) cannot have more than 8 characters" }

    Color.decode("#$value")
  }
