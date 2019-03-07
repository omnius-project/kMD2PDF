package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.Settings
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.cssSelector

/**
 * <table></table> element
 */
class Table(settings: Settings) : Element("table", settings) {

  enum class BorderCollapse {
    SEPARATE, COLLAPSE
  }

  val th = TableHeader(settings)
  val thead = TableHead(settings)
  val tbody = TableBody(settings)
  val td = TableData(settings)
  val tr = TableRow(settings)

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