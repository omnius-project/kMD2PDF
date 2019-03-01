package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.utility.cssSelector

/**
 * <table></table> element
 */
class Table(fontSize: Double, fontFamily: FontFamily) : Element("table", fontSize, fontFamily) {

  enum class BorderCollapse {
    SEPARATE, COLLAPSE
  }

  val th = TableHeader(fontSize, fontFamily)
  val thead = TableHead(fontSize, fontFamily)
  val tbody = TableBody(fontSize, fontFamily)
  val td = TableData(fontSize, fontFamily)
  val tr = TableRow(fontSize, fontFamily)

  var borderCollapse = BorderCollapse.COLLAPSE

  fun thead(style: TableHead.() -> Unit) = thead.style()

  fun tbody(style: TableBody.() -> Unit) = tbody.style()

  fun tr(style: TableRow.() -> Unit) = tr.style()

  fun th(style: TableHeader.() -> Unit) = th.style()

  fun td(style: TableData.() -> Unit) = td.style()

  override fun toCss(): String {
    css.add(cssSelector("table") {
      attributes {
        "border-collapse" to borderCollapse.name.toLowerCase()
      }
    })
    return super.toCss()
  }
}