package me.chill.style

import me.chill.style.elements.*
import me.chill.style.elements.headers.*
import me.chill.style.elements.lists.OrderedList
import me.chill.style.elements.lists.UnorderedList
import me.chill.style.elements.table.Table

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
  abstract val strikethrough: Strikethrough
  abstract val hr: Ruler

  fun getElements() = listOf(
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
    strikethrough,
    hr
  )
}
