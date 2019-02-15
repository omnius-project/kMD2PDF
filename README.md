# kMD2PDF
Markdown to PDF conversion library written in Kotlin.

## Sample Usage
### Default styling
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
  document.convertToPDF("C:/Users/Chill/Desktop/Document.pdf")
}
```

### Custom styling using style DSL
```kotlin
fun createDSLStyle() = PDFStyle.createStyle {
  code {
    fontFamily {
      + "Fira Code"
    }
  }

  headerOne {
    fontColor = Color.RED
  }

  bold {
    fontColor = Color.PINK
  }

  paragraph {
    fontFamily {
      +"Roboto"
    }
  }

  link {
    fontFamily {
      +"Times New Romans"
    }
  }
}

fun main() {
  val customStyle = createDSLStyle()
  val dslDocument = MarkdownDocument("C:/Users/Chill/Desktop/README.md", customStyle)
  dslDocument.convertToPDF()
}
```

## To do
* [ ] Move to custom parser library 