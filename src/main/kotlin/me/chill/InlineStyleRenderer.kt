package me.chill

class InlineStyleRenderer {
  private val attributes = mutableMapOf<String, String>()

  fun attribute(attributeName: String, attributeValue: String): InlineStyleRenderer {
    attributes[attributeName] = attributeValue
    return this
  }

  fun renderStyle() = attributes.entries.joinToString(" ") { "${it.key}: ${it.value};" }
}