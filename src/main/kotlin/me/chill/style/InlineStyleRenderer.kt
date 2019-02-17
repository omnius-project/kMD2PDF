package me.chill.style

class InlineStyleRenderer {
  private val attributes = mutableMapOf<String, String>()

  fun <T> attribute(attributeName: String, attributeValue: T?): InlineStyleRenderer {
    attributeValue?.let { attributes[attributeName] = it.toString() }
    return this
  }

  fun renderStyle() = attributes.entries.joinToString(" ") { "${it.key}: ${it.value};" }
}