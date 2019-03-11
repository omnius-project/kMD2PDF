# Changelog
0.2.0 of the library just launched! It has been an incredibly exciting journey creating this version as it now puts a 
lot more control into the hands of you (the developer). This page will go over all the features added as well as the
changes that have been applied to the library.

The example repository for kMD2PDF will cover examples of the new features, they can be found 
[here.](https://github.com/omnius-project/kMD2PDF-examples)

## PDF document configuration system overhaul
0.1.2 adopted a rather confusing approach to customizing the PDF that was exported by the library. This is due to the 
lack of decoupling between classes and having the `MarkdownDocument` class perform more than 1 duty. It did not make
sense to give a `MarkdownDocument` a style.

Hence, in 0.2.0, the configuration system has been streamlined, to allow for a whole slew of customizations to be 
presented that would otherwise be incredible confusing to implement.

Sticking to the principle that a class should perform a single duty, the `MarkdownDocument` class has been relieved of
its unwanted job of handling the parsing and conversion of the markdown document. Instead, its presence is simply to 
perform the essential validation for the input files to ensure that it is a valid markdown file on the user's machine.

### markdownConverter block
The `MarkdownConverter` has picked up where `MarkdownDocument` left off and implemented a builder pattern to house the
various configurations that the converted PDF document will have. Kotlin allows for a neater approach to the builder
pattern, through the use of a DSL, and that's exactly what kMD2PDF adopted.

Now, if you wish to set up any configurations for the PDF document that's to be exported, use the `markdownConverter`
block, which will return a `MarkdownConverter` that is able to export the intended `MarkdownDocument`. 

All `MarkdownConverter`s must, at the very least, specify the `MarkdownDocument` to convert using the `document` method.

```kotlin
val userHome = System.getenv("user.home")
val document = MarkdownDocument("$userHome/Desktop/README.md")
val converter = markdownConverter {
  document(document)
}
```

### Specifying target location
In 0.1.2, the target location can be configured by passing the location to the `MarkdownDocument#toPDF` method. 
Things are a little different in 0.2.0. The target location is configured within the `markdownConverter` block using
the `targetLocation` method.

The target file location must still be a file that has the `.pdf` extension.

```kotlin
val userHome = System.getenv("user.home")
val document = MarkdownDocument("$userHome/Desktop/README.md")
val converter = markdownConverter {
  document(document)
  targetLocation("$userHome/Documents/test.pdf")
}
```

### Styling documents
Instead of passing in a customized `Style` object to the `MarkdownDocument`, the custom style is configured within the
`markdownConverter` block. The customization DSL for the `Style` object has not changed much from 0.1.2.

```kotlin
markdownConverter {
  style {
    p {
      fontFamily {
        +"Fira Code" // You sick heathen
      }
    }
  }
}
```

### Settings system overhaul
In 0.1.2, configuring global settings is a tedious process that isn't too Kotlin-like as you will have to specify
each configuration as a separate argument into the `createStyle` method which can make for an unpleasant experience if
you want to configure the settings that are not in the original order. As such, it has been re-worked in 0.2.0 to have
all settings to be configured by a `settings` block (which creates a `Settings` object) and now you have finer control
over each global setting.

Pass the created `Settings` object as a parameter to a new `Style` object or to the `style` method.

```kotlin
markdownConverter {
  val settings = settings {
    monospaceFont = FontFamily("Source Code Pro")
    font = FontFamily("Lato")
  }
  
  style(settings) { }
}
```

#### Global monospace fonts
Alongside the changes made to the settings system, you can now specify a global monospace font that will be used by 
both inline code and code blocks. This is set using the `monospaceFont` property within the `settings` block.

## Headers and footers
With 0.2.0, you are now able to customize the header and footers of exported documents, allowing for the inclusion of 
additional details on the documents for every single page.

This can be done within the `style` block using `header` or `footer`. 

The header and footer is sectioned into 3 areas, **left**, **center** and **right**.

```kotlin
style {
  header/footer {
    left/right/center {
      +"Content"
    }
  }
}
```

### Page numbers
Page numbers can also be added to the header/footer using the `pageNumber` method. This method can receive 2 kinds of
inputs, one for some text to be prepended to each page number, and another for some text to be appended to each page
number.

The page number is rendered in the following manner: `<prepend text>Page number<append text>`.

```kotlin
header/footer {
  left/right/center {
    pageNumber("Prepended text", "Appended text")
  }
}
```

## Document properties
In version 0.1.2, document customization was limited to just customizing the design of the contents of the document.
This may bring about limitations with the kinds of designs that can be rendered. As such, 0.2.0 introduces the ability
to customize the way the document is set up. 

Document properties are configured using the `documentProperties` block within the `markdownConverter` block.

```kotlin
markdownConverter {
  documentProperties { }
}
```

This customization includes some key components that are configurable using 
[CSS `@page` selectors:](https://www.w3.org/TR/css-page-3/)

### Document size
The document size can be configured using one of three constructors belonging to the `DocumentSize` class. This is 
inline with the available options for the `size` property in [CSS.](https://www.w3.org/TR/css-page-3/#page-size-prop)

All measurements are done in inches.

```kotlin
documentProperties {
  // Specifying width by height
  size(DocumentSize(11, 13))
  
  // Specifying preset document size (with optional orientation)
  size(DocumentSize(PageSize.A3, DocumentOrientation.HORIZONTAL))
  
  // Specifying the size to be auto (this is default)
  size(DocumentSize(true))
}
```

### Page margins
The CSS specification for `@page` also allows developers to decide how much margin each page should have. There are 2 
variations to this - first, the margins of every single page and second, the margins of pages that show up on the left
side or right side.

#### All page margins
The margins of all pages can be configured using the `margins` method.

```kotlin
documentProperties {
  margins(Box(1.1, 2.0))
}
```

#### Left/right page margins
The margins for either left pages or right pages can be configured using the `leftPageMargins` or `rightPageMargins`
methods.

More information for how these influence the document can be found 
[here.](https://www.w3.org/TR/css-page-3/#spread-pseudos)

```kotlin
documentProperties {
  leftPageMargins(Box(1.0, 2.1))
  rightPageMargins(Box(0.2, 1.1))
}
```

## Table of contents
Another exciting feature in 0.2.0 is the auto-generation of the table of contents based off the headers present within
your document.

Now you can optionally include an auto-generated table of contents page at the beginning of your document by configuring
the table of contents properties within the `documentProperties` block and setting the `isVisible` property to **true**.

```kotlin
documentProperties {
  tableOfContents {
    isVisible = true
  }
}
```

### Nesting levels
You can also specify the level of heading that is included in the table of content using the `nestedLevel` property.

```kotlin
tableOfContents {
  nestedLevel = 4 // Displays up till header 4
}
```

## Supporting list-style-image
0.2.0 supports the `list-style-image` attribute from CSS, so now you can use images for the list items. This is done
by using the `listStyleImage` attribute within either the `ul` or `ol` block.

```kotlin
style {
  ol/ul {
    listStyleImage = "https://github.com/omnius-project/kMD2PDF/blob/master/art/logo.png"
  }
}
```

## Supporting task list items
0.2.0 also introduces support for task list items, a concept that GitHub adopted for their own 
[flavour of markdown](https://help.github.com/en/articles/basic-writing-and-formatting-syntax#task-lists). This 
feature was not available to the commonmark-java library (the parsing library kMD2PDF uses). In order to provide 
support for rendering task list items, a custom list item renderer had to be added to process these types of list items.

Now, all you need to do to render task lists are to simply use the following markdown syntax:

```markdown
* [ ] Uncompleted task
* [X] Completed task
```

## Themes
Ever wanted dark theme PDFs? Even if your answer was no, it's here! You can now export your documents in either light
or dark theme. Simply specify the desired theme in the `settings` block and voila!

```kotlin
settings {
  theme = Settings.Theme.DARK
}
```

Some notable changes would be that the text will now all be white against black, and code blocks change from a solarized
light color scheme to a solarized dark color scheme.

## Bug fixes
* [Issue #18](https://github.com/omnius-project/kMD2PDF/issues/18)
* [Issue #19](https://github.com/omnius-project/kMD2PDF/issues/19)