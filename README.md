[![Build Status](https://travis-ci.org/woojiahao/kMD2PDF.svg?branch=master)](https://travis-ci.org/woojiahao/kMD2PDF)

![](art/logo.png)
> Easy to use Markdown to PDF library

## Sample Usage
### Default styling
```kotlin
fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF()
}
```

### Specifying .pdf location
```kotlin
// File must be named with .pdf extension
fun main() {
  val document = MarkdownDocument("C:/Users/Chill/Desktop/README.md")
  document.convertToPDF("C:/Users/Chill/Desktop/Document.pdf")
}
```

### Custom styling using style DSL
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

## To do
* [ ] Move to custom parser library 