package com.github.woojiahao

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
import org.xhtmlrenderer.render.BlockBox
import org.xhtmlrenderer.simple.extend.FormSubmissionListener
import java.io.FileInputStream

class MediaReplacedElementFactory(private val superFactory: ReplacedElementFactory) : ReplacedElementFactory {

  override fun createReplacedElement(
    c: LayoutContext?,
    box: BlockBox?,
    uac: UserAgentCallback?,
    cssWidth: Int,
    cssHeight: Int
  ): ReplacedElement? {

    box ?: return null

    val defaultElement = superFactory.createReplacedElement(c, box, uac, cssWidth, cssHeight)

    with (box.element) {
      if (nodeName != "img" || getAttribute("class") != "local") return defaultElement

      val imageSource = getAttribute("src")
      FileInputStream(imageSource).use {
        val fsImage: FSImage? = ITextFSImage(Image.getInstance(IOUtils.toByteArray(it)))
        if (fsImage != null) {
          if ((cssWidth != -1) || (cssHeight != -1)) fsImage.scale(cssWidth, cssHeight)
          return ITextImageElement(fsImage)
        }
      }
    }

    return defaultElement
  }

  override fun remove(e: Element?) = superFactory.remove(e)

  override fun setFormSubmissionListener(listener: FormSubmissionListener?) =
    superFactory.setFormSubmissionListener(listener)

  override fun reset() = superFactory.reset()

}