# kMD2PDF
Markdown to PDF conversion library written in Kotlin.

## Sample Usage
### Default parsing
```kotlin
val document = MarkdownDocument("C:/Users/Chill/Desktop/notes.md")
document.convertToPDF()
```

### Custom styling
```kotlin
class Styles : MarkdownStyling() {
  override fun h1(header: Header) {
  }
}
```

## To do
* [ ] Move to custom parser library 