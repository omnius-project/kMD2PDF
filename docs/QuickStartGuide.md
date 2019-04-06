# Quick Start Guide
All examples are taken from the [examples repository.](https://github.com/omnius-project/kMD2PDF-examples)

```kotlin
val document 
  get() = MarkdownDocument("resources/markdown-all-in-one.md")
```

## Default styling
Example [here.](https://github.com/omnius-project/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/DefaultStyling.kt)
```kotlin
fun main() {
  val converter = markdownConverter {
    document(document)
  }
  converter.convert()
}
```

## Specifying PDF location
Example [here.](https://github.com/omnius-project/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/SpecifyingPDFLocation.kt)
```kotlin
fun main() {
  val converter = markdownConverter {
    document(document)
    targetLocation("${System.getProperty("user.home")}/Desktop/exported.pdf")
  }
  converter.convert()
}
```

## Conversion success
Example [here.](https://github.com/omnius-project/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/OnCompleteAction.kt)
```kotlin
fun main() {
  val converter = markdownConverter {
    document(document)
  }
  val conversionResult = converter.convert()
  conversionResult.success {
    if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(it)
  }
}
```

## Conversion failure
Example [here.](https://github.com/omnius-project/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/OnErrorAction.kt)
```kotlin
fun main() {
  val converter = markdownConverter {
    document(document)
  }
  val conversionStatus = converter.convert()
  conversionStatus.failure {
    if (it is FileNotFoundException) println("File is currently already open")
  }
}
```

## Custom styling using style DSL
Example [here.](https://github.com/omnius-project/kMD2PDF-examples/blob/master/src/main/kotlin/com/github/woojiahao/SimpleStyling.kt)

More on this subject can be found on the documentation site [here.](https://omnius-project.github.io/kMD2PDF/#/StyleDSL)
```kotlin
fun main() {
  val converter = markdownConverter {
    document(document)

    settings {
      fontSize = 16.0.px
      font = FontFamily("Roboto", "Lato")
      monospaceFont = FontFamily("Fira Code")
    }

    style {
      p {
        textColor = c("455A64")
      }

      ul {
        listStyleType = SQUARE
      }

      selector("tr:nth-child(even)") {
        "background-color" to "#f2f2f2"
      }
    }
  }
  converter.convert()
}
```
