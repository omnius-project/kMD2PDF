package me.chill.style

import me.chill.style.elements.*
import me.chill.style.elements.headers.*
import me.chill.style.elements.lists.OrderedList
import me.chill.style.elements.lists.UnorderedList

/**
 * Default styling for an exported PDF.
 *
 * Supply a [baseFontSize] to be applied to all elements unless otherwise
 * specified. This [baseFontSize] will influence the scaling of each
 * header.
 *
 * Supply a [baseFontFamily] to be applied to all elements unless otherwise
 * specified. [InlineCode] will change this font to use a monospace font instead.
 */
class PDFStyle(
  private val baseFontSize: Double = 16.0,
  private val baseFontFamily: FontFamily = FontFamily("sans-serif")
) {

  var h1 = HeaderOne(baseFontSize, baseFontFamily.clone())
    private set

  var h2 = HeaderTwo(baseFontSize, baseFontFamily.clone())
    private set

  var h3 = HeaderThree(baseFontSize, baseFontFamily.clone())
    private set

  var h4 = HeaderFour(baseFontSize, baseFontFamily.clone())
    private set

  var h5 = HeaderFive(baseFontSize, baseFontFamily.clone())
    private set

  var h6 = HeaderSix(baseFontSize, baseFontFamily.clone())
    private set

  var inlineCode = InlineCode(baseFontSize, FontFamily("monospace").clone())
    private set

  var bold = Bold(baseFontSize, baseFontFamily.clone())
    private set

  var paragraph = Paragraph(baseFontSize, baseFontFamily.clone())
    private set

  var link = Link(baseFontSize, baseFontFamily.clone())
    private set

  var ul = UnorderedList(baseFontSize, baseFontFamily.clone())
    private set

  var ol = OrderedList(baseFontSize, baseFontFamily.clone())
    private set

  companion object {
    /**
     * Creates a custom PDFStyle object using a custom DSL.
     *
     * Supply a [baseFontSize] to be applied to all element unless otherwise specified.
     * [baseFontSize] will influence the scaling of each header.
     */
    fun createStyle(
      baseFontSize: Double = 16.0,
      baseFontFamily: FontFamily = FontFamily("sans-serif"),
      styleFunction: PDFStyle.() -> Unit
    ) = PDFStyle(baseFontSize, baseFontFamily.clone()).apply { styleFunction() }
  }

  fun inlineCode(style: InlineCode.() -> Unit) {
    this.inlineCode = InlineCode(baseFontSize, FontFamily("monospace").clone()).apply { style() }
  }

  fun bold(style: Bold.() -> Unit) {
    this.bold = Bold(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun paragraph(style: Paragraph.() -> Unit) {
    this.paragraph = Paragraph(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun link(style: Link.() -> Unit) {
    this.link = Link(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun h1(style: HeaderOne.() -> Unit) {
    this.h1 = HeaderOne(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun h2(style: HeaderTwo.() -> Unit) {
    this.h2 = HeaderTwo(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun h3(style: HeaderThree.() -> Unit) {
    this.h3 = HeaderThree(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun h4(style: HeaderFour.() -> Unit) {
    this.h4 = HeaderFour(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun h5(style: HeaderFive.() -> Unit) {
    this.h5 = HeaderFive(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun h6(style: HeaderSix.() -> Unit) {
    this.h6 = HeaderSix(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun ul(style: UnorderedList.() -> Unit) {
    this.ul = UnorderedList(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun ol(style: OrderedList.() -> Unit) {
    this.ol = OrderedList(baseFontSize, baseFontFamily.clone()).apply { style() }
  }

  fun matchHeaderLevel(headerLevel: Int) =
    when (headerLevel) {
      1 -> h1
      2 -> h2
      3 -> h3
      4 -> h4
      5 -> h5
      6 -> h6
      else -> h1
    }
}
