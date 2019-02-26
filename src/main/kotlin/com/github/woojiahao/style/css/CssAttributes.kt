package com.github.woojiahao.style.css

class CssAttributes {
  private val attributes = mutableMapOf<String, String?>()

  fun <T> add(attributeName: String, attributeValue: T?): CssAttributes {
    attributes[attributeName] = attributeValue?.toString()
    return this
  }

  fun append(attributes: CssAttributes) = this.attributes.putAll(attributes.attributes)

  fun remove(attributeName: String): CssAttributes {
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