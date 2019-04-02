package com.github.woojiahao.modifiers

import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.style.utility.Box
import com.github.woojiahao.style.utility.Measurement
import com.github.woojiahao.style.utility.Measurement.Type.PIXELS
import com.lowagie.text.Image
import org.apache.commons.io.IOUtils
import org.w3c.dom.Element
import org.xhtmlrenderer.extend.FSImage
import org.xhtmlrenderer.extend.ReplacedElement
import org.xhtmlrenderer.extend.ReplacedElementFactory
import org.xhtmlrenderer.extend.UserAgentCallback
import org.xhtmlrenderer.layout.LayoutContext
import org.xhtmlrenderer.pdf.ITextFSImage
import org.xhtmlrenderer.pdf.ITextImageElement
import org.xhtmlrenderer.pdf.ITextUserAgent
import org.xhtmlrenderer.render.BlockBox
import org.xhtmlrenderer.simple.extend.FormSubmissionListener
import java.io.FileInputStream
import kotlin.math.min

class MediaReplacedElementFactory(
  private val documentProperties: DocumentProperties,
  private val superFactory: ReplacedElementFactory
) : ReplacedElementFactory {

  private val pageWidth: Double
    get() = calculateAvailableWidth(documentProperties.margins)

  private val leftPageWidth: Double?
    get() {
      with(documentProperties) {
        leftPageMargins ?: return null
        return calculateAvailableWidth(leftPageMargins)
      }
    }

  private val rightPageWidth: Double?
    get() {
      with(documentProperties) {
        rightPageMargins ?: return null
        return calculateAvailableWidth(rightPageMargins)
      }
    }

  override fun createReplacedElement(
    c: LayoutContext?,
    box: BlockBox?,
    uac: UserAgentCallback?,
    cssWidth: Int,
    cssHeight: Int
  ): ReplacedElement? {

    box ?: return null
    uac as ITextUserAgent

    val defaultElement = superFactory.createReplacedElement(c, box, uac, cssWidth, cssHeight)

    with(box.element) {
      if (nodeName != "img" || getAttribute("class") != "local") return defaultElement

      FileInputStream(getAttribute("src")).use {
        val bytes = IOUtils.toByteArray(it)
        val image = Image.getInstance(bytes).apply {
          val isLeftPage = c?.page?.isLeftPage ?: false
          val isRightPage = c?.page?.isRightPage ?: false

          val availableWidth = when {
            isLeftPage -> leftPageWidth
            isRightPage -> rightPageWidth
            else -> pageWidth
          } ?: pageWidth

          val originalImageWidth = scaledWidth.toDouble()

          val chosenWidth = min(originalImageWidth, availableWidth)
          val scalingFactor =
            if (chosenWidth != originalImageWidth) originalImageWidth / chosenWidth
            else 1.0

          val dotsPerPixel = uac.sharedContext.dotsPerPixel

          val scaledHeight = ((scaledHeight.toDouble()) / scalingFactor) * dotsPerPixel
          val scaledWidth = chosenWidth * dotsPerPixel

          scaleAbsolute(scaledWidth.toFloat(), scaledHeight.toFloat())
        }

        val fsImage: FSImage? = ITextFSImage(image)
        return fsImage?.let { img -> ITextImageElement(img) } ?: defaultElement
      }
    }
  }

  private fun calculateAvailableWidth(margins: Box<Measurement<Double>>): Double {
    val documentWidth = documentProperties.size.width
    val documentWidthInPixels = documentWidth.convert(PIXELS)

    val marginsWidth = margins.left + margins.right
    val marginsWidthInPixels = marginsWidth.convert(PIXELS)

    val availableWidth = documentWidthInPixels - marginsWidthInPixels
    return availableWidth.convert(PIXELS).value
  }

  override fun remove(e: Element?) = superFactory.remove(e)

  override fun setFormSubmissionListener(listener: FormSubmissionListener?) =
    superFactory.setFormSubmissionListener(listener)

  override fun reset() = superFactory.reset()

}