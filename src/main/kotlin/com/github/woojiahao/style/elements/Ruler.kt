package com.github.woojiahao.style.elements

import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import com.github.woojiahao.style.utility.FontFamily
import java.awt.Color.BLACK

class Ruler(fontSize: Double, fontFamily: FontFamily) : Element("hr", fontSize, fontFamily) {
  override var border: BorderBox? = BorderBox(Border(1.0, SOLID, BLACK))
}