package com.github.woojiahao.utility.extensions

fun <V> Map<String?, V?>.getOrNull(key: String) = getOrDefault(key, null)