package me.chill.utility.extensions

import java.io.File

fun File.isFileType(vararg fileType: String) = fileType.any { extension == it }