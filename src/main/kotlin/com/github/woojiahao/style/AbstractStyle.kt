package com.github.woojiahao.style

import com.github.woojiahao.style.css.CssStyle
import com.github.woojiahao.style.elements.*
import com.github.woojiahao.style.elements.code.CodeBlock
import com.github.woojiahao.style.elements.code.InlineCode
import com.github.woojiahao.style.elements.headers.*
import com.github.woojiahao.style.elements.lists.OrderedList
import com.github.woojiahao.style.elements.lists.UnorderedList
import com.github.woojiahao.style.elements.table.Table

abstract class AbstractStyle {
  abstract val h1: HeaderOne
  abstract val h2: HeaderTwo
  abstract val h3: HeaderThree
  abstract val h4: HeaderFour
  abstract val h5: HeaderFive
  abstract val h6: HeaderSix
  abstract val inlineCode: InlineCode
  abstract val codeBlock: CodeBlock
  abstract val strong: Bold
  abstract val p: Paragraph
  abstract val a: Link
  abstract val ul: UnorderedList
  abstract val ol: OrderedList
  abstract val blockquote: BlockQuote
  abstract val img: Image
  abstract val table: Table
  abstract val del: Strikethrough
  abstract val hr: Ruler

  val elements by lazy {
    listOf(
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
      table,
      table.thead,
      table.tbody,
      table.th,
      table.td,
      table.tr,
      del,
      hr
    )
  }

  val customStyles = mutableListOf<CssStyle>()

  /**
   * Returns all the styles from the current implementation of [AbstractStyle].
   *
   * Styles for [elements] go first, then [customStyles].
   */
  fun getStyles(): String {
    val separator = "\n\n"
    val elementStyles = elements.joinToString(separator) { it.toCss() }
    val customStyles = customStyles.joinToString(separator) { it.toString() }
    return "$elementStyles$separator$customStyles"
  }
}
