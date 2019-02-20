package com.github.woojiahao.style

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

  infix fun <T> String.to(attributeValue: T?) {
    attributes[this] = attributeValue?.toString()
  }

  fun toCss() =
    attributes
      .entries
      .filter { it.value != null }
      .joinToString("\n") { "\t${it.key}: ${it.value};" }
}