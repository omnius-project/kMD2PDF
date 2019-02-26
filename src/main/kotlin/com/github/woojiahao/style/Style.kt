package com.github.woojiahao.style

import com.github.woojiahao.style.css.CssAttributes
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.elements.*
import com.github.woojiahao.style.elements.code.CodeBlock
import com.github.woojiahao.style.elements.code.InlineCode
import com.github.woojiahao.style.elements.document.DocumentHeader
import com.github.woojiahao.style.elements.headers.*
import com.github.woojiahao.style.elements.lists.OrderedList
import com.github.woojiahao.style.elements.lists.UnorderedList
import com.github.woojiahao.style.elements.table.Table
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.*

class Style(
  baseFontSize: Double = 16.0,
  baseFontFamily: FontFamily = FontFamily(SANS_SERIF)
) {

  companion object {
    inline fun createStyle(
      baseFontSize: Double = 16.0,
      baseFontFamily: FontFamily = FontFamily(SANS_SERIF),
      style: Style.() -> Unit
    ) = Style(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  val h1 = HeaderOne(baseFontSize, baseFontFamily.clone())
  val h2 = HeaderTwo(baseFontSize, baseFontFamily.clone())
  val h3 = HeaderThree(baseFontSize, baseFontFamily.clone())
  val h4 = HeaderFour(baseFontSize, baseFontFamily.clone())
  val h5 = HeaderFive(baseFontSize, baseFontFamily.clone())
  val h6 = HeaderSix(baseFontSize, baseFontFamily.clone())
  val inlineCode = InlineCode(baseFontSize, FontFamily(MONOSPACE).clone())
  val codeBlock = CodeBlock(baseFontSize, FontFamily(MONOSPACE).clone())
  val strong = Bold(baseFontSize, baseFontFamily.clone())
  val p = Paragraph(baseFontSize, baseFontFamily.clone())
  val a = Link(baseFontSize, baseFontFamily.clone())
  val ul = UnorderedList(baseFontSize, baseFontFamily.clone())
  val ol = OrderedList(baseFontSize, baseFontFamily.clone())
  val blockquote = BlockQuote(baseFontSize, baseFontFamily.clone())
  val img = Image(baseFontSize, baseFontFamily.clone())
  val table = Table(baseFontSize, baseFontFamily.clone())
  val del = Strikethrough(baseFontSize, baseFontFamily.clone())
  val hr = Ruler(baseFontSize, baseFontFamily.clone())

  val header = DocumentHeader(baseFontSize, baseFontFamily.clone())

  val elements = listOf(
    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    inlineCode,
    codeBlock,
    strong,
    p,
    a,
    ul,
    ol,
    blockquote,
    img,
    img.figCaption,
    table,
    table.thead,
    table.tbody,
    table.th,
    table.td,
    table.tr,
    del,
    hr,
    header,
    header.left,
    header.right,
    header.center
  )

  val customStyles = mutableListOf<CssSelector>()


  inline fun inlineCode(style: InlineCode.() -> Unit) = inlineCode.style()

  inline fun codeBlock(style: CodeBlock.() -> Unit) = codeBlock.style()

  inline fun strong(style: Bold.() -> Unit) = strong.style()

  inline fun p(style: Paragraph.() -> Unit) = p.style()

  inline fun a(style: Link.() -> Unit) = a.style()

  inline fun ul(style: UnorderedList.() -> Unit) = ul.style()

  inline fun ol(style: OrderedList.() -> Unit) = ol.style()

  inline fun blockquote(style: BlockQuote.() -> Unit) = blockquote.style()

  inline fun img(style: Image.() -> Unit) = img.style()

  inline fun table(style: Table.() -> Unit) = table.style()

  inline fun del(style: Strikethrough.() -> Unit) = del.style()

  inline fun hr(style: Ruler.() -> Unit) = hr.style()

  inline fun h1(style: HeaderOne.() -> Unit) = h1.style()

  inline fun h2(style: HeaderTwo.() -> Unit) = h2.style()

  inline fun h3(style: HeaderThree.() -> Unit) = h3.style()

  inline fun h4(style: HeaderFour.() -> Unit) = h4.style()

  inline fun h5(style: HeaderFive.() -> Unit) = h5.style()

  inline fun h6(style: HeaderSix.() -> Unit) = h6.style()

  inline fun header(style: DocumentHeader.() -> Unit) = header.style()

  inline fun selector(selector: String, style: CssAttributes.() -> Unit) {
    customStyles.add(
      CssSelector(
        selector,
        CssAttributes().apply { style() }
      )
    )
  }

  fun getStyles(): String {
    val separator = "\n\n"
    val elementStyles = elements.joinToString(separator) { it.toCss() }
    val customStyles = customStyles.joinToString(separator) { it.toCss() }
    return "$elementStyles$separator$customStyles"
  }
}
