# Conversion Targets
0.2.2 introduces the ability to change the converted file types. Currently the following conversions are permitted:

* `.md` to `.pdf`
* `.md` to `.html` and `.css`

To change the conversion target of the `MarkdownConverter`, use `conversionTarget`.

```kotlin
val converter = markdownConverter {
  // ...
  conversionTarget(MarkdownConverter.ConversionTarget.HTML)
  // ...
}

// The API to convert remains the same
converter.convert()
```

## Markdown to PDF
Markdown documents are converted directly to PDF document.

## Markdown to HTML/CSS
The conversion plays a little differently here. In order to prevent and ensure flexibility in the library, instead of
bundling the CSS with HTML into the same file, the library creates a folder for the exported documents and export the
2 separately. Currently, there are some bugs regarding the CSS styling, most of which can be alleviated by applying 
additional styles to the HTML. Potential fixes are in the pipeline. 