# About Box
This section explains the `Box` object that is used for properties in the [Style DSL](StyleDSL.md) like `padding` and 
`margin`.

`Box` aims to simulate the idea of modifying the 4 sides of an element separately.

For instance, `padding = Box(2.0, 3.0, 4.0, 5.0)` simulates the padding on each side to be 2.0, 3.0, 4.0 and 5.0 in 
top-right-bottom-left order, the same order CSS uses for its [box model](https://www.w3schools.com/css/css_boxmodel.asp).

The available constructors for `Box` are:

```kotlin
// Applies 10.0 to all sides on the box
Box(10.0)               

// Applies 5.0 to both vertical sides of the box and 10.0 to the horizontal sides of the box
Box(5.0, 10.0)          

// Applies 2.0 to top, 3.0 to right, 4.0 to bottom and 5.0 to left
Box(2.0, 3.0, 4.0, 5.0) 
```