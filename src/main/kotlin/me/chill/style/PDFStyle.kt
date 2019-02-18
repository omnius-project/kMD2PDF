package me.chill.style

import me.chill.style.FontFamily.BaseFontFamily.MONOSPACE
import me.chill.style.FontFamily.BaseFontFamily.SANS_SERIF
import me.chill.style.elements.Bold
import me.chill.style.elements.InlineCode
import me.chill.style.elements.Link
import me.chill.style.elements.Paragraph
import me.chill.style.elements.headers.*
import me.chill.style.elements.lists.OrderedList
import me.chill.style.elements.lists.UnorderedList

/**
 * Styling for an exported PDF.
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
  private val baseFontFamily: FontFamily = FontFamily(SANS_SERIF)
) {

  companion object {
    /**
     * Creates a custom PDFStyle object using the DSL.
     *
     * Supply a [baseFontSize] to be applied to all element unless otherwise specified.
     * [baseFontSize] will influence the scaling of each header.
     */
    fun createStyle(
      baseFontSize: Double = 16.0,
      baseFontFamily: FontFamily = FontFamily(SANS_SERIF),
      styleFunction: PDFStyle.() -> Unit
    ) = PDFStyle(baseFontSize, baseFontFamily.clone()).apply { styleFunction() }
  }

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

  var inlineCode = InlineCode(baseFontSize, FontFamily(MONOSPACE).clone())
    private set

  var bold = Bold(baseFontSize, baseFontFamily.clone())
    private set

  var p = Paragraph(baseFontSize, baseFontFamily.clone())
    private set

  var link = Link(baseFontSize, baseFontFamily.clone())
    private set

  var ul = UnorderedList(baseFontSize, baseFontFamily.clone())
    private set

  var ol = OrderedList(baseFontSize, baseFontFamily.clone())
    private set

  /**
   * Style [InlineCode] element.
   */
  fun inlineCode(style: InlineCode.() -> Unit) = inlineCode.style()

  /**
   * Style [Bold] element.
   */
  fun bold(style: Bold.() -> Unit) = bold.style()

  /**
   * Style [Paragraph] element.
   */
  fun p(style: Paragraph.() -> Unit) = p.style()

  /**
   * Style [Link] element.
   */
  fun link(style: Link.() -> Unit) = link.style()

  /**
   * Style [UnorderedList] element.
   */
  fun ul(style: UnorderedList.() -> Unit) = ul.style()

  /**
   * Style [OrderedList] element.
   */
  fun ol(style: OrderedList.() -> Unit) = ol.style()

  /**
   * Style [HeaderOne] element.
   */
  fun h1(style: HeaderOne.() -> Unit) = h1.style()

  /**
   * Style [HeaderTwo] element.
   */
  fun h2(style: HeaderTwo.() -> Unit) = h2.style()

  /**
   * Style [HeaderThree] element.
   */
  fun h3(style: HeaderThree.() -> Unit) = h3.style()

  /**
   * Style [HeaderFour] element.
   */
  fun h4(style: HeaderFour.() -> Unit) = h4.style()

  /**
   * Style [HeaderFive] element.
   */
  fun h5(style: HeaderFive.() -> Unit) = h5.style()

  /**
   * Style [HeaderSix] element.
   */
  fun h6(style: HeaderSix.() -> Unit) = h6.style()

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
