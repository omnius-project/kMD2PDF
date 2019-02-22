package com.github.woojiahao.style.elements.table

import com.github.woojiahao.style.FontFamily
import com.github.woojiahao.style.elements.Element

/**
 * <table></table> element
 */
class Table(
  fontSize: Double = 16.0,
  fontFamily: FontFamily = FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)
) : Element("table", fontSize, fontFamily) {

  enum class BorderCollapse {
    SEPARATE, COLLAPSE
  }

  val th = TableHeader(fontSize, fontFamily.clone())
  val thead = TableHead(fontSize, fontFamily.clone())
  val tbody = TableBody(fontSize, fontFamily.clone())
  val td = TableData(fontSize, fontFamily.clone())
  val tr = TableRow(fontSize, fontFamily.clone())

  var borderCollapse = BorderCollapse.COLLAPSE

  fun thead(style: TableHead.() -> Unit) = thead.style()

  fun tbody(style: TableBody.() -> Unit) = tbody.style()

  fun tr(style: TableRow.() -> Unit) = tr.style()

  fun th(style: TableHeader.() -> Unit) = th.style()

  fun td(style: TableData.() -> Unit) = td.style()

  override fun toCss(): String {
    attributes.add("border-collapse", borderCollapse.name.toLowerCase())
    return super.toCss()
  }
}