# Style DSL
kMD2PDF introduces a DSL for creating custom styles for the exported PDFs. The DSL is inspired by the TornadoFX 
[Type Safe CSS DSL.](https://github.com/edvin/tornadofx/wiki/Type-Safe-CSS)

The DSL aims to be as close to CSS as possible so that it is intuitive.

If there isn't a specific configuration available for your customization needs, you can use a 
[custom selector.](StyleDSL.md?id=custom-selectors)

## Getting started
Styles are configured within the `markdownConverter` block using the `style` block.

```kotlin
val converter = markdownConverter {
  style { }
}
```

## Global settings 
Global settings are configured using the `settings` block in the `markdownConverter` block.

```kotlin
markdownConverter {
  settings { }
}
```

**Note:** The `settings` block must come before the `style` block, otherwise, the global settings will not apply.

The following settings are available for configuration.

### Font size
Headers are scaled in relation to the global font size, following [these](https://www.w3schools.com/tags/tag_hn.asp) 
constants.

**Default:** `16.0.px`
```kotlin
settings {
  fontSize = 16.0.px
}
```

### Font family
**Default:** `FontFamily(FontFamily.BaseFontFamily.SANS_SERIF)`

```kotlin
settings {
  font = FontFamily("Roboto", "Lato")
}
```

### Monospace font
**Default:** `FontFamily(FontFamily.BaseFontFamily.MONOSPACE)`

```kotlin
settings {
  monospaceFont = FontFamily("Fira Code")
}
```

### Theme
**Default:** `Settings.Theme.LIGHT`

```kotlin
settings {
  theme = Settings.Theme.DARK
}
```

## Elements
Each element can be configured through a method call corresponding to the element name used in HTML with some minor
exceptions that will be specified later on.
 
All the elements available to be customised can be found
[here.](https://github.com/woojiahao/kMD2PDF/tree/documentation/src/main/kotlin/com/github/woojiahao/style/elements) 

For instance, to configure the `Paragraph` element, invoke the `Style#p` method within the DSL.

```kotlin
style {
  p {
    // Configuration for Paragraph goes here
  }
}
```

**Note:** To customize the table specific elements like `th` and `tr`, they must be done within the `table` method call.
```kotlin
table {
  th {
    // Configuration for th goes here
  }
}
```

## Themes
0.2.0 introduced [theming](Changelog.md?id=Themes) and with that, a new set of classes `CssProperty` has been created
as delegated properties to manage the configuration of settings depending on the theme of the document.

```kotlin
val textColor by CssProperty(c("21"), c("EE"))
```

In this instance, if the theme is `DARK`, the text color used will be `#EEEEEE` and if the theme is `LIGHT`, the text
color used will be `#212121`.

Some elements will have specified different colors to use depending on theme such as tables, code blocks and text 
colors.

## Common Configurations
This section covers the shared configuration settings available to all element types. The examples will be using 
applying the styles to the `Paragraph` element, but these configurations can be applied to any other element.

The table summarizes all the common configurations available:

| Configuration   | Data Type              | Light                    | Dark      |
| --------------- | ---------------------- | ------------------------ | --------- |
| fontSize        | Double                 | `16.0.px`                | Same      |
| fontFamily      | FontFamily             | `FontFamily(SANS_SERIF)` | Same      |
| textColor       | Color?                 | `#212121`                | `#EEEEEE` |
| backgroundColor | Color?                 | `null`                   | Same      |
| fontWeight      | Element.FontWeight     | `null`                   | Same      |
| textDecoration  | Element.TextDecoration | `null`                   | Same      |
| border          | BorderBox              | `BorderBox(Border())`    | Same      |
| borderRadius    | Box<Double>            | `Box(0.0.px)`            | Same      |
| padding         | Box<Double>?           | `null`                   | Same      |
| margin          | Box<Double>?           | `null`                   | Same      |

### Font Size
```kotlin
p {
  fontSize = 12.0.px
}
```

### Font Family
```kotlin
p {
  fontFamily {
    +"Roboto"
    +"Lato"
  }
}
```

### Text Color
Colors can be retrieved using the function `StyleUtility#c`.

```kotlin
p {
  textColor = c("D32F2F")
}
```

### Background Color
Colors can be retrieved using the function `StyleUtility#c`.

```kotlin
p {
  backgroundColor = c("1976D2")
}
```

### Font Weight
The enumeration constants can be found [here.](https://github.com/woojiahao/kMD2PDF/blob/documentation/src/main/kotlin/com/github/woojiahao/style/elements/Element.kt)

```kotlin
p {
  fontWeight = Element.FontWeight.NORMAL
}
```

### Text Decoration
The enumeration constants can be found [here.](https://github.com/woojiahao/kMD2PDF/blob/documentation/src/main/kotlin/com/github/woojiahao/style/elements/Element.kt)

```kotlin
p {
  textDecoration = Element.TextDecoration.UNDERLINE
}
```

### Border
Border is stored as a `BorderBox` so you can customise the border on each side of the element.

The example below sets the border of all paragraphs to be `2.0px dashed rgb(0, 255, 0)` on all sides, then overrides
the left border to be `4.0px dotted rgb(255, 0, 0)`.

```kotlin
p {
  border {
    all(2.0.px dashed Color.GREEN)
    
    left(4.0.px dotted Color.RED)
  }
}
```

### Border Radius
To configure the border radius, a `Box` object is used. More about this object can be found [here.](AboutBox.md)

The CSS equivalent of the example below is: `border-radius: 4.0px 3.0px 10.0px 2.0px`.

```kotlin
p {
  borderRadius = Box(4.0.px, 3.0.px, 10.0.px, 2.0.px)
}
```

### Padding
The example below sets the padding on all sides of the paragraph element to 10.0px. More on the `Box` object can be 
found [here.](AboutBox.md)

```kotlin
p {
  padding = Box(10.0.px)
}
```


### Margin
The example below sets the margin on all sides of the paragraph element to 10.0px. More on the `Box` object can be 
found [here.](AboutBox.md)

```kotlin
p {
  margin = Box(10.0.px)
}
```

## Unordered And Ordered List Configurations
These are configurations only accessible when customizing `UnorderedList` and `OrderedList`.

| Configuration     | Data Type              | Default Value                    |
| ----------------- | ---------------------- | -------------------------------- |
| listStyleType     | List.ListStyleType     | `List.ListStyleType.CIRCLE`      |
| listStylePosition | List.ListStylePosition | `List.ListStylePosition.OUTSIDE` |

### List Style Type
```kotlin
ol {
  listStyleType = List.ListStyleType.HEBREW
}

ul {
  listStyleType = List.ListStyleType.SQUARE
}
```

### List Style Position
```kotlin
ul {
  listStylePosition = List.ListStylePosition.INSIDE
}
```

### List Style Images
```koltin
ul {
  listStyleImage = "https://via.placeholder.com/150"
}
```

## Table Configurations
These are configurations only accessible when customizing `Table`. As specified above, to access and customize 
table-specific elements, they have to be done within a `table` method call:

```kotlin
table {
  thead { }
  tbody { }
  th { }
  tr { }
  td { }
}
```

`Table` also introduces a new configuration setting:

| Configuration  | Data Type            | Default Value                   |
| -------------- | -------------------- | ------------------------------- |
| borderCollpase | Table.BorderCollapse | `Table.BorderCollapse.COLLAPSE` |

### Border Collapse
**Note:** The typo is based on an actual typo in the codebase, this will be fixed in the next mini release.

```kotlin
table {
  borderCollpase = Table.BorderCollpase.SEPARATE
}
```

## Custom Selectors
Custom selectors are loaded to the bottom of the style sheet. Within the selector, assign CSS attributes using the 
following pattern:

```
<attribute_name :: String> to <attribute_value :: String>
```

For example, this selector creates a striped pattern for tables.

```kotlin
createStyle {
  selector("tr:nth-child(even)") {
    "background-color" to "#f2f2f2"
  }
}
```