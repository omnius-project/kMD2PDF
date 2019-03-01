package com.github.woojiahao.style

import com.github.woojiahao.style.css.CssAttributes
import com.github.woojiahao.style.css.CssSelector
import com.github.woojiahao.style.elements.*
import com.github.woojiahao.style.elements.code.CodeBlock
import com.github.woojiahao.style.elements.code.InlineCode
import com.github.woojiahao.style.elements.document.DocumentFooter
import com.github.woojiahao.style.elements.document.DocumentHeader
import com.github.woojiahao.style.elements.headers.*
import com.github.woojiahao.style.elements.lists.OrderedList
import com.github.woojiahao.style.elements.lists.UnorderedList
import com.github.woojiahao.style.elements.table.Table
import com.github.woojiahao.style.utility.FontFamily
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.MONOSPACE
import com.github.woojiahao.style.utility.FontFamily.BaseFontFamily.SANS_SERIF

class Style(
  fontSize: Double = 16.0,
  private val font: FontFamily = FontFamily(SANS_SERIF),
  private val monospaceFont: FontFamily = FontFamily(MONOSPACE)
) {

  companion object {
    inline fun createStyle(
      fontSize: Double = 16.0,
      font: FontFamily = FontFamily(SANS_SERIF),
      monospaceFont: FontFamily = FontFamily(MONOSPACE),
      style: Style.() -> Unit
    ) = Style(fontSize, font, monospaceFont).apply { style() }
  }

  private val baseFont
    get() = font.clone()

  private val baseMonospaceFont
    get() = monospaceFont.clone()

  val h1 = HeaderOne(fontSize, baseFont)
  val h2 = HeaderTwo(fontSize, baseFont)
  val h3 = HeaderThree(fontSize, baseFont)
  val h4 = HeaderFour(fontSize, baseFont)
  val h5 = HeaderFive(fontSize, baseFont)
  val h6 = HeaderSix(fontSize, baseFont)
  val inlineCode = InlineCode(fontSize, baseMonospaceFont)
  val codeBlock = CodeBlock(fontSize, baseMonospaceFont)
  val strong = Bold(fontSize, baseFont)
  val p = Paragraph(fontSize, baseFont)
  val a = Link(fontSize, baseFont)
  val ul = UnorderedList(fontSize, baseFont)
  val ol = OrderedList(fontSize, baseFont)
  val blockquote = BlockQuote(fontSize, baseFont)
  val img = Image(fontSize, baseFont)
  val table = Table(fontSize, baseFont)
  val del = Strikethrough(fontSize, baseFont)
  val hr = Ruler(fontSize, baseFont)
  val header = DocumentHeader(fontSize, baseFont)
  val footer = DocumentFooter(fontSize, baseFont)

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
      header.center,
      footer,
      footer.left,
      footer.right,
      footer.center
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

  inline fun footer(style: DocumentFooter.() -> Unit) = footer.style()

  inline fun selector(selector: String, style: CssAttributes.() -> Unit) {
    customStyles += CssSelector(selector, CssAttributes().apply { style() })
  }

  fun getStyles(): String {
    val separator = "\n\n"
    val elementStyles = elements.joinToString(separator) { it.toCss() }
    val customStyles = customStyles.joinToString(separator) { it.toCss() }
    return "$elementStyles$separator$customStyles"
  }
}
