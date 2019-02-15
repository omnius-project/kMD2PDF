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

### Specifying .pdf location
```kotlin
// File must be named with .pdf extension
fun main(args: Array<String>) {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF("C:/Users/Chill/Desktop/Test.pdf")
}
```

### Custom styling using overriding
```kotlin
// Create a function that generates the custom style
fun createDSLStyle() = PDFStyle.createStyle {
    code {
      fontSize = 16.0
      fontFamily = listOf("Hack")
    }

    headerOne {
      fontColor = Color.RED
    }

    bold {
      fontColor = Color.PINK
    }

    paragraph {
      fontFamily = listOf("Roboto")
    }

    link {
      fontFamily = listOf("Monaco")
    }
  }
  
// Use the function and pass the custom style to MarkdownDocument
fun main() {
  val customStyle = createDSLStyle()
  val dslDocument = MarkdownDocument("C:/Users/Chill/Desktop/README.md", customStyle)
  dslDocument.convertToPDF()
}
```

### Custom styling using DSL
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