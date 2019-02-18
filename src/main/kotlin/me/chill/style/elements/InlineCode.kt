package me.chill.style.elements

import me.chill.style.FontFamily
import me.chill.style.FontFamily.BaseFontFamily.MONOSPACE
import me.chill.utility.c

/**
 * Inline <code> element styles.
 */
class InlineCode(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(MONOSPACE)
) : Element(fontSize, fontFamily) {
  override var fontColor = c("FF3D00")
  override var backgroundColor = c("#F5F5F5")
  var borderRadius = 5.0
  var padding = 3.0
}