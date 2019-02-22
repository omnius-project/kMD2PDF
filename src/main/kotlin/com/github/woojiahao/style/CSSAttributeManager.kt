package com.github.woojiahao.style

class CSSAttributeManager {
  private val attributes = mutableMapOf<String, String?>()

  fun <T> add(attributeName: String, attributeValue: T?): CSSAttributeManager {
    attributes[attributeName] = attributeValue?.toString()
    return this
  }

  fun remove(attributeName: String): CSSAttributeManager {
    attributes.remove(attributeName)
    return this
  }

  infix fun <T> String.to(attributeValue: T?) {
    attributes[this] = attributeValue?.toString()
  }

  /**
   * Returns all non-null valued CSS attributes stored in the [CSSAttributeManager].
   */
  fun toCss() =
    attributes
      .entries
      .filter { it.value != null }
      .joinToString("\n") { "\t${it.key}: ${it.value};" }
}