# Quick Start Guide
## Default styling
```kotlin
fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF()
}
```

## Specifying .pdf location
The target file location must end with a `.pdf` extension
```kotlin
fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF("C:/Users/Chill/Desktop/Document.pdf")
}
```

## Custom styling using style DSL
More information of the DSL can be found at [Custom DSL](customDSL.md)
```kotlin
fun createDSLStyle() = PDFStyle.createStyle {
  code {
    fontFamily {
      +"Fira Code"
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
  val dslDocument = MarkdownDocument(
    "C:/Users/Chill/Desktop/README.md", 
    customStyle
  )
  dslDocument.convertToPDF()
}
```

## onComplete
Called when the conversion is completed and no errors occurred.
```kotlin
fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md").onComplete {
    println("Document converted successfully")
  }
}
```

## onError
Called when the conversion encounters an error.
```kotlin
fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md").onError {
    if (it is FileNotFoundException) {
      println("Cannot convert file. File is still open")
    }
  }
}
```