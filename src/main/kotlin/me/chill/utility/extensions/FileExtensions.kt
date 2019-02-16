package me.chill.utility.extensions

import java.io.File

/**
 * Returns the target file has an extension that matches any of
 * the supplied [fileTypes].
 */
fun File.isFileType(vararg fileTypes: String) = fileTypes.any { extension == it }