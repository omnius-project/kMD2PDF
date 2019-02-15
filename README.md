# kMD2PDF
Markdown to PDF conversion library written in Kotlin.

## Sample Usage
### Default style
```kotlin
fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF()
}
```

### Custom styling
```kotlin
// Create a class to override PDFStyle
class CustomStyle : PDFStyle() {
  override val code = object : Code() {
    override val fontSize = 32.0
    override val fontFamily = listOf("Hack")
  }
  
  override val headerOne = object : HeaderOne() {
    override val fontColor = Color.RED
  }
}

// Use the custom class when creating the MarkdownDocument
fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md", CustomStyle())
  document.convertToPDF()
}
```

## To do
* [ ] Move to custom parser library 