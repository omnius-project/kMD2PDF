package com.github.woojiahao.style.css

class CssAttributes {
  private val attributes = mutableMapOf<String, String?>()

  fun append(attributes: CssAttributes) = this.attributes.putAll(attributes.attributes)

  fun <T> add(name: String, value: T?): CssAttributes {
    addAttribute(name, value)
    return this
  }

  fun remove(name: String): CssAttributes {
    attributes.remove(name)
    return this
  }

  infix fun <T> String.to(value: T?) = addAttribute(this, value)

  fun toCss() =
    attributes
      .entries
      .filter { it.value != null }
      .joinToString("\n") { "\t${it.key}: ${it.value};" }

  private fun <T> addAttribute(name: String, value: T?) {
    attributes[name] = value?.toString()
  }
}