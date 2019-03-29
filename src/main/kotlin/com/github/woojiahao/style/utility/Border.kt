package com.github.woojiahao.style.utility

import com.github.woojiahao.style.utility.Border.BorderStyle.*
import com.github.woojiahao.utility.cssColor
import java.awt.Color

data class Border(
  var borderWidth: Measurement<Double> = 0.0.px,
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

  fun clear() = set(0.0.px, NONE, Color.BLACK)

  private fun set(
    borderWidth: Measurement<Double>,
    borderStyle: BorderStyle,
    borderColor: Color?
  ) {
    this.borderWidth = borderWidth
    this.borderStyle = borderStyle
    this.borderColor = borderColor
  }

  override fun toString() = "$borderWidth ${borderStyle.name.toLowerCase()} ${borderColor?.cssColor()}"
}

infix fun Measurement<Double>.dotted(color: Color?) = Border(this, DOTTED, color)

infix fun Measurement<Double>.dashed(color: Color?) = Border(this, DASHED, color)

infix fun Measurement<Double>.solid(color: Color?) = Border(this, SOLID, color)

infix fun Measurement<Double>.double(color: Color?) = Border(this, DOUBLE, color)

infix fun Measurement<Double>.groove(color: Color?) = Border(this, GROOVE, color)

infix fun Measurement<Double>.ridge(color: Color?) = Border(this, RIDGE, color)

infix fun Measurement<Double>.inset(color: Color?) = Border(this, INSET, color)

infix fun Measurement<Double>.outset(color: Color?) = Border(this, OUTSET, color)

infix fun Measurement<Double>.none(color: Color?) = Border(this, NONE, color)

infix fun Measurement<Double>.hidden(color: Color?) = Border(this, HIDDEN, color)