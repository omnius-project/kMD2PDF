package me.chill.style

import me.chill.style.Border.BorderStyle.*
import java.awt.Color

/**
 * Handles the border to be rendered. Border properties can be set individually,
 * or using CSS-like DSL syntax: `5.0 dotted Color.RED`
 */
data class Border(
  var borderWidth: Double = 0.0,
  var borderStyle: BorderStyle = NONE,
  var borderColor: Color? = Color.BLACK
) {
  enum class BorderStyle {
    DOTTED,
    DASHED,
    SOLID,
    DOUBLE,
    GROOVE,
    RIDGE,
    INSET,
    OUTSET,
    NONE,
    HIDDEN
  }

  /**
   * Sets border to be [DOTTED].
   */
  infix fun Double.dotted(color: Color?) = setBorder(this, DOTTED, color)

  /**
   * Sets border to be [DASHED].
   */
  infix fun Double.dashed(color: Color?) = setBorder(this, DASHED, color)

  /**
   * Sets border to be [SOLID].
   */
  infix fun Double.solid(color: Color?) = setBorder(this, SOLID, color)

  /**
   * Sets border to be [DOUBLE].
   */
  infix fun Double.double(color: Color?) = setBorder(this, DOUBLE, color)

  /**
   * Sets border to be [GROOVE].
   */
  infix fun Double.groove(color: Color?) = setBorder(this, GROOVE, color)

  /**
   * Sets border to be [RIDGE].
   */
  infix fun Double.ridge(color: Color?) = setBorder(this, RIDGE, color)

  /**
   * Sets border to be [INSET].
   */
  infix fun Double.inset(color: Color?) = setBorder(this, INSET, color)

  /**
   * Sets border to be [OUTSET].
   */
  infix fun Double.outset(color: Color?) = setBorder(this, OUTSET, color)

  /**
   * Sets border to be [NONE].
   */
  infix fun Double.none(color: Color?) = setBorder(this, NONE, color)

  /**
   * Sets border to be [HIDDEN].
   */
  infix fun Double.hidden(color: Color?) = setBorder(this, HIDDEN, color)

  /**
   * Resets the border to defaults of [borderWidth] - 0.0, [borderStyle] - [NONE],
   * [borderColor] - [Color.BLACK].
   */
  fun clearBorder() = setBorder(0.0, NONE, Color.BLACK)

  /**
   * Sets the border preferences.
   */
  private fun setBorder(borderWidth: Double, borderStyle: BorderStyle, borderColor: Color?) {
    this.borderWidth = borderWidth
    this.borderStyle = borderStyle
    this.borderColor = borderColor
  }
}