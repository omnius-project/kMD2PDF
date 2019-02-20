package com.github.woojiahao.utility

import com.github.woojiahao.utility.extensions.isFileType
import org.apache.commons.lang3.SystemUtils.*
import java.io.File

/**
 * Returns all folders with .ttf or .otf files based on the base folders
 * from [getBaseFontDirectories].
 */
fun getFontDirectories() = getFontDirectorySubFolders(getBaseFontDirectories().map { File(it) })

/**
 * Retrieves the common font directories stored on an OS.
 */
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

/**
 * Traverses through [baseFontDirectories] to find sub-folders that
 * contain .ttf or .otf files, returning the parent folder names of all these
 * sub-folders (inclusive of the each [baseFontDirectories].
 */
private fun getFontDirectorySubFolders(baseFontDirectories: List<File>) =
  baseFontDirectories
    .map {
      it
        .walk()
        .filter { f -> f.isFileType("ttf", "otf") }
        .map { f -> f.parent }
        .distinct()
        .toList()
    }
    .flatten()
