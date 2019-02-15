package me.chill.utility

import org.apache.commons.lang3.SystemUtils.*


fun getFontDirectories(): List<String> =
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
