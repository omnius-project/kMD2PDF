package com.github.woojiahao

import com.github.woojiahao.MarkdownConverter.ConversionTarget.HTML
import com.github.woojiahao.MarkdownConverter.ConversionTarget.PDF
import com.github.woojiahao.conversion_handlers.HtmlConversionHandler
import com.github.woojiahao.conversion_handlers.PdfConversionHandler
import com.github.woojiahao.generators.CssGenerator
import com.github.woojiahao.generators.HtmlGenerator
import com.github.woojiahao.modifiers.figure.FigureExtension
import com.github.woojiahao.modifiers.toc.TableOfContentsNodeVisitor
import com.github.woojiahao.modifiers.toc.TableOfContentsVisitor
import com.github.woojiahao.modifiers.yaml.parseYaml
import com.github.woojiahao.properties.DocumentProperties
import com.github.woojiahao.properties.PagePropertiesManager
import com.github.woojiahao.style.Style
import com.github.woojiahao.style.elements.Element
import com.github.woojiahao.utility.extensions.isFileType
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.toc.TocExtension
import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet
import java.io.File

class MarkdownConverter private constructor(
  markdownDocument: MarkdownDocument,
  documentStyle: Style,
  targetLocation: File,
  conversionTarget: ConversionTarget,
  documentProperties: DocumentProperties
) {

  enum class ConversionTarget(val requiredExtension: String?, val isFolder: Boolean) {
    PDF("pdf", false), HTML(null, true)
  }

  private val extensions = listOf(
    TaskListExtension.create(),
    TablesExtension.create(),
    StrikethroughExtension.create(),
    TocExtension.create(),
    YamlFrontMatterExtension.create(),
    FigureExtension.create(markdownDocument.file)
  )

  private val options = MutableDataSet().apply { set(Parser.EXTENSIONS, extensions) }

  private val parser = Parser.builder(options).build()

  private val htmlRenderer = HtmlRenderer.builder(options).build()

  private val parsedDocument = parser.parse(markdownDocument.file.readText())

  private val parsedDocumentBody = htmlRenderer.render(parsedDocument)

  private val tableOfContentsNodeVisitor = TableOfContentsNodeVisitor(
    TableOfContentsVisitor(documentProperties.tableOfContentsSettings)
  ).also { it.visit(parsedDocument) }

  private val yamlFrontMatterVisitor = AbstractYamlFrontMatterVisitor().also { it.visit(parsedDocument) }

  private val yaml = parseYaml(yamlFrontMatterVisitor.data).also {
    fun <T> List<Element>.updateStyles(input: T?, update: Element.(T) -> Unit) {
      input?.let { forEach { e -> e.update(it) } }
    }

    with(documentStyle) {
      regularElements.updateStyles(it.font) { font -> fontFamily.value = font }
      monospaceElements.updateStyles(it.monospaceFont) { monospaceFont -> fontFamily.value = monospaceFont }
      elements.updateStyles(it.fontSize) { fontSize -> this.fontSize.value = fontSize }

      // Updating the theme has to be propagated to each of the elements
      it.theme.let { theme ->
        theme ?: return@let
        settings.theme = theme
        elements.forEach { e -> e.attributes.forEach { attr -> attr.theme = theme } }
      }
    }
  }

  private val pagePropertiesManager = PagePropertiesManager(documentProperties, documentStyle)

  private val cssGenerator = CssGenerator(documentStyle, pagePropertiesManager, documentProperties)

  private val htmlGenerator = HtmlGenerator(
    documentStyle,
    parsedDocumentBody.trim(),
    documentProperties,
    tableOfContentsNodeVisitor.visitor.getTableOfContents()
  )

  val body = htmlGenerator.generate()

  val css = cssGenerator.generate()

  private val conversionHandler = when (conversionTarget) {
    PDF -> PdfConversionHandler(body, css, targetLocation, mapOf("documentProperties" to documentProperties))
    HTML -> HtmlConversionHandler(body, css, targetLocation)
  }

  fun convert() = conversionHandler.convert()

  open class Builder {
    private var document: MarkdownDocument? = null
    private var style = Style()
    private var targetLocation: String? = null
    private var documentProperties = DocumentProperties.Builder().build()
    private var conversionTarget = PDF

    fun document(document: MarkdownDocument): Builder {
      this.document = document
      return this
    }

    fun style(style: Style): Builder {
      this.style = style
      return this
    }

    fun targetLocation(targetLocation: String): Builder {
      this.targetLocation = targetLocation
      return this
    }

    fun documentProperties(documentProperties: DocumentProperties): Builder {
      this.documentProperties = documentProperties
      return this
    }

    fun conversionTarget(conversionTarget: ConversionTarget): Builder {
      this.conversionTarget = conversionTarget
      return this
    }

    fun build(): MarkdownConverter {
      val doc = document
      check(doc != null) { "Markdown document must be set using document()" }

      val targetFile = createTargetOutputFile(targetLocation, conversionTarget)
      val (isTargetFileValid, invalidReason) = validateOutputFile(targetFile)
      check(isTargetFileValid) { invalidReason }

      return MarkdownConverter(
        doc,
        style,
        targetFile,
        conversionTarget,
        documentProperties
      )
    }

    private fun validateOutputFile(outputFile: File): Pair<Boolean, String> {
      with(conversionTarget) {
        if (isFolder) {
          if (outputFile.extension.isNotEmpty())
            return false to "Target location should not have extension"
        } else {
          requiredExtension
              ?: return false to "Required extension cannot be null for folder string"
          if (!outputFile.isFileType(requiredExtension))
            return false to "Target location must have a .$requiredExtension extension"
        }
      }

      return true to ""
    }

    private fun createTargetOutputFile(filePath: String?, conversionTarget: ConversionTarget) =
        filePath?.let { File(it) } ?: createFileRelativeToDocument(conversionTarget)

    private fun createFileRelativeToDocument(conversionTarget: ConversionTarget): File {
      with(document!!.file) {
        check(this.parentFile != null) { "File must have parent folder" }

        val fileName =
            if (!conversionTarget.isFolder) "$nameWithoutExtension.${conversionTarget.requiredExtension}"
            else nameWithoutExtension

        return File(this.parentFile, fileName)
      }
    }
  }
}