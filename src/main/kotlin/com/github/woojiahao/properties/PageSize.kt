package com.github.woojiahao.properties

import com.github.woojiahao.utility.extensions.toInches

/**
 * [width] and [height] in inches.
 */
enum class PageSize(
  val sizeName: String,
  private val width: Double,
  private val height: Double
) {
  A5("A5", 148.toInches(), 210.toInches()),
  A4("A4", 210.toInches(), 297.toInches()),
  A3("A3", 297.toInches(), 420.toInches()),
  B5("B5", 176.toInches(), 250.toInches()),
  B4("B4", 250.toInches(), 353.toInches()),
  JIS_B5("JIS_B5", 182.toInches(), 257.toInches()),
  JIS_B4("JIS_B4", 257.toInches(), 364.toInches()),
  LETTER("letter", 8.5, 11.0),
  LEGAL("legal", 8.5, 14.0),
  LEDGER("ledger", 11.0, 17.0);
}
