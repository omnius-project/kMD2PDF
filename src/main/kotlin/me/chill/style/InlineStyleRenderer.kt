package me.chill.style

class InlineStyleRenderer {
  private val attributes = mutableMapOf<String, String>()

  fun attribute(attributeName: String, attributeValue: String?): InlineStyleRenderer {
    attributeValue?.let { attributes[attributeName] = it }
    return this
  }

  fun renderStyle() = attributes.entries.joinToString(" ") { "${it.key}: ${it.value};" }
}