package me.chill.utility

import me.chill.utility.extensions.isFileType
import org.apache.commons.lang3.SystemUtils.*
import java.io.File

fun getFontDirectories() = getFontDirectorySubFolders(getBaseFontDirectories())

private fun getBaseFontDirectories() =
  when {
    IS_OS_WINDOWS -> listOf(
      "${System.getenv("WINDIR")}\\Fonts\\"
    )
    IS_OS_MAC || IS_OS_MAC_OSX -> listOf(
      "/System/Library/Fonts",
      "/Library/Fonts",
      "~/Library/Fonts",
      "/Network/Library/Fonts"
    )
    IS_OS_LINUX -> listOf(
      "/usr/share/fonts",
      "/usr/local/share/fonts",
      "~/.fonts"
    )
    else -> emptyList()
  }

private fun getFontDirectorySubFolders(baseFontDirectories: List<String>) =
  baseFontDirectories
    .map {
      File(it)
        .walk()
        .filter { f -> f.isFileType("ttf", "otf") }
        .map { f -> f.parent }
        .distinct()
        .toList()
    }
    .flatten()
