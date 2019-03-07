package com.github.woojiahao.style.elements

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.utility.Border
import com.github.woojiahao.style.utility.Border.BorderStyle.SOLID
import com.github.woojiahao.style.utility.BorderBox
import java.awt.Color.BLACK

class Ruler(settings: Settings) : Element("hr", settings) {
  override var border: BorderBox? = BorderBox(Border(1.0, SOLID, BLACK))
}