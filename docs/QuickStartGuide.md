# Quick Start Guide
All examples are taken from the [examples repository.](https://github.com/woojiahao/kMD2PDF-examples)

### Default styling
Example [here.](https://github.com/woojiahao/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/basic/DefaultStyling.kt)
```kotlin
fun main() {
  val markdownDocument = MarkdownDocument("resources/markdown-all-in-one.md")
  markdownDocument.toPDF()
}
```

### Specifying PDF location
Example [here.](https://github.com/woojiahao/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/basic/SpecifyingPDFLocation.kt)
```kotlin
fun main() {
  val markdownDocument = MarkdownDocument("resources/markdown-all-in-one.md")
  markdownDocument.toPDF("${System.getProperty("user.home")}/Desktop/exported.pdf")
}
```

### onComplete
Example [here.](https://github.com/woojiahao/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/basic/OnCompleteAction.kt)
```kotlin
fun main() {
  val markdownDocument = MarkdownDocument("resources/markdown-all-in-one.md")
  markdownDocument.onComplete {
    println("Conversion success - opening document")

    if (Desktop.isDesktopSupported()) {
      Desktop.getDesktop().open(it)
    } else {
      System.out.println("Awt Desktop is not supported!")
    }
  }
  markdownDocument.toPDF()
}
```

### onError
Example [here.](https://github.com/woojiahao/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/basic/OnErrorAction.kt)
```kotlin
fun main() {
  val markdownDocument = MarkdownDocument("resources/markdown-all-in-one.md")
  markdownDocument.onError {
    println("An error occurred")

    if (it is FileNotFoundException) {
      println("File is currently already open")
    }
  }
  markdownDocument.toPDF()
}
```

### Custom styling using style DSL
Example [here.](https://github.com/woojiahao/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/basic/SimpleStyling.kt)

More on this subject can be found on the documentation site [here.](StyleDSL.md)
```kotlin
fun main() {
  val customStyle = createDSLStyle()
  val markdownDocument = MarkdownDocument("resources/markdown-all-in-one.md", customStyle)
  markdownDocument.toPDF()
}

fun createDSLStyle() = Style
    .createStyle(
        16.0,
        FontFamily("Roboto", "Lato")
    ) {
      p {
        textColor = c("455A64")
      }

      inlineCode {
        fontFamily {
          +"Fira Code"
        }
      }

      ul {
        listStyleType = List.ListStyleType.SQUARE
      }

      selector("tr:nth-child(even)") {
        "background-color" to "#f2f2f2"
      }
    }
```