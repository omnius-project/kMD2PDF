package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.utility.px
import java.awt.Color

/**
 * Inline <code> element styles.
 */
class InlineCode(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily("monospace")
) : Element(fontSize, fontFamily) {
  override var fontColor: Color = Color.decode("#FF3D00")
  override var backgroundColor: Color? = Color.decode("#F5F5F5")
  var borderRadius = 5.0
  var padding = 3.0
}