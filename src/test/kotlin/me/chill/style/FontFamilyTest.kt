package me.chill.style

import org.junit.Test
import kotlin.test.assertEquals

class FontFamilyTest {
  @Test
  fun `Creating FontFamily with empty fonts parameter leaves fonts empty`() {
    val fontFamily = FontFamily()
    fontFamily.checkFontFamilySize(0)
  }

  @Test
  fun `Creating FontFamily with some fonts will load those fonts into the FontFamily`() {
    val fontFamily = FontFamily("Roboto", "Arial", "Consolas")
    fontFamily.checkFontList("Roboto", "Arial", "Consolas")
  }

  @Test
  fun `Emptying FontFamily will clear any fonts loaded`() {
    val fontFamily = FontFamily("Fira Code", "Monaco", "Droid Sans Mono", "DejaVu Sans Mono")
    fontFamily.checkFontFamilySize(4)
    fontFamily.emptyFontFamily()
    fontFamily.checkFontFamilySize(0)
  }

  @Test
  fun `DSL FontFamily can add new font using unary plus operator`() {
    val fontFamily = FontFamily("Lato")
    fontFamily.checkFontList("Lato")
    fontFamily.addFont {
      +"Roboto"
    }
    fontFamily.checkFontList("Lato", "Roboto")
  }

  @Test
  fun `FontFamily toString will return the font family in CSS font-family format`() {
    val fontFamily = FontFamily("Roboto", "Raleway")
    val expectedString = "Roboto, Raleway"
    fontFamily.checkToString(expectedString)
  }

  @Test
  fun `FontFamily toString adds single quotes to font names with more than 2 words`() {
    val fontFamily = FontFamily("Fira Code", "Roboto", "Raleway", "Droid Sans Mono")
    val expectedString = "'Fira Code', Roboto, Raleway, 'Droid Sans Mono'"
    fontFamily.checkToString(expectedString)
  }

  private fun FontFamily.checkToString(expectedToString: String) {
    assertEquals(expectedToString, toString())
  }

  private fun FontFamily.checkFontFamilySize(expectedSize: Int) {
    assertEquals(expectedSize, getFonts().size)
  }

  private fun FontFamily.checkFontList(vararg fonts: String) {
    assertEquals(fonts.toList(), getFonts())
  }

  private fun FontFamily.addFont(func: FontFamily.() -> Unit) {
    func()
  }
}