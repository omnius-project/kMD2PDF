# Custom DSL
kMD2PDF introduces a DSL for creating custom styles for the exported PDFs.

## Getting Started
The DSL returns a customised version of `PDFStyle` with the styles you configure.

First invoke the `PDFStyle#createStyle` static method and use the DSL.

```kotlin
val customisedStyle = PDFStyle.createStyle {
  // Style configuration goes here
}
```

**Note:** For the remainder of the guide, it'll be assumed that `createStyle` has been statically imported.

## Global Settings
These configurations are set during creation of the custom style and will apply to all elements unless otherwise 
tweaked.

### Font Size
Headers are scaled in relation to the global font size, following [these](https://www.w3schools.com/tags/tag_hn.asp) 
constants.

**Default:** 16.0
```kotlin
createStyle(18.0) { }
```

### Font Family
Code fonts do not inherit this global setting, instead, they default to use `FontFamily("monospace")` configured on the
user's device.

**Default:** `FontFamily("sans-serif")`

```kotlin
createStyle(baseFontFamily = FontFamily("Roboto", "Lato")) { }
```

## Elements
Each element can be configured through a method call corresponding to the element name. More on the different elements
can be found [here.](https://github.com/woojiahao/kMD2PDF/tree/master/src/main/kotlin/me/chill/style/elements) 

For instance, to configure the `Paragraph` element, invoke the `PDFStyle#paragraph` method within the DSL.

```kotlin
createStyle {
  paragraph {
    // Configuration for Paragraph goes here
  }
}
```

## Common Configurations
This section covers the shared configuration settings available to all element types. The examples will be using 
applying the styles to the `Paragraph` element, but these configurations can be applied to any other element.

### Font Size
**Data type:** `Double`

**Default:** 16.0

```kotlin
paragraph {
  fontSize = 12.0
}
```

### Font Family
**Data type:** `FontFamily`

**Default:** `FontFamily("sans-serif")`

```kotlin
paragraph {
  fontFamily {
    +"Roboto"
    +"Lato"
  }
}
```

### Font Color
Colors can be retrieved using the function `StyleUtility#c`.

**Data type:** `Color`

**Default:** `#000000|Color.BLACK|c("000000")`

```kotlin
paragraph {
  fontColor = c("D32F2F")
}
```

### Background Color
Colors can be retrieved using the function `StyleUtility#c`.

**Data type:** `Color?`

**Default:** `null`

```kotlin
paragraph {
  backgroundColor = c("1976D2")
}
```

### Font Weight
**Data type:** `Element.FontWeight`

**Default:** `Element.FontWeight.NORMAL`

```kotlin
paragraph {
  fontWeight = Element.FontWeight.NORMAL
}
```

### Text Decoration
**Data type:** `Element.TextDecoration`

**Default:** `Element.TextDecoration.NONE`

```kotlin
paragraph {
  textDecoration = Element.TextDecoration.UNDERLINE
}
```

### Border
**Data type:** `Border`

**Default:** `Border()`

```kotlin
paragraph {
  border {
    2.0 dotted c("E65100")
  }
}
```

## Inline Code Configuration
Configurations only the `InlineCode` element has.

### Border Radius
**Data type:** `Double`

**Default:** 5.0

```kotlin
inlineCode {
  borderRadius = 2.0
}
``` 

### Padding
Padding is applied in all directions.

**Data type:** `Double`

**Default:** 3.0

```kotlin
inlineCode {
  padding = 5.0
}
```