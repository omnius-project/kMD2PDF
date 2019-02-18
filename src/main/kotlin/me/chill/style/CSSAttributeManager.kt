package me.chill.style

class CSSAttributeManager {
  private val attributes = mutableMapOf<String, String?>()

  fun <T> attribute(attributeName: String, attributeValue: T?): CSSAttributeManager {
    attributes[attributeName] = attributeValue?.toString()
    return this
  }

  fun removeAttribute(attributeName: String): CSSAttributeManager {
    attributes.remove(attributeName)
    return this
  }

  fun toCss() =
    attributes
      .entries
      .filter { it.value != null }
      .joinToString("\n") { "\t${it.key}: ${it.value};" }
}