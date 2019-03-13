package com.github.woojiahao.style.utility

import com.github.woojiahao.style.utility.Border.BorderStyle.*
import com.github.woojiahao.utility.cssColor
import java.awt.Color

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

  fun clear() = set(0.0, NONE, Color.BLACK)

  private fun set(
    borderWidth: Double,
    borderStyle: BorderStyle,
    borderColor: Color?
  ) {
    this.borderWidth = borderWidth
    this.borderStyle = borderStyle
    this.borderColor = borderColor
  }

  override fun toString() = "${borderWidth.px} ${borderStyle.name.toLowerCase()} ${borderColor?.cssColor()}"
}

infix fun Double.dotted(color: Color?) = Border(this, DOTTED, color)

infix fun Double.dashed(color: Color?) = Border(this, DASHED, color)

infix fun Double.solid(color: Color?) = Border(this, SOLID, color)

infix fun Double.double(color: Color?) = Border(this, DOUBLE, color)

infix fun Double.groove(color: Color?) = Border(this, GROOVE, color)

infix fun Double.ridge(color: Color?) = Border(this, RIDGE, color)

infix fun Double.inset(color: Color?) = Border(this, INSET, color)

infix fun Double.outset(color: Color?) = Border(this, OUTSET, color)

infix fun Double.none(color: Color?) = Border(this, NONE, color)

infix fun Double.hidden(color: Color?) = Border(this, HIDDEN, color)