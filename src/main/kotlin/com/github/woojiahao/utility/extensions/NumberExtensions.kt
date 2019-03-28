package com.github.woojiahao.utility.extensions

import com.github.woojiahao.style.utility.`in`

fun Double.toInches() = (this / 25.4).`in`

fun Int.toInches() = (this / 25.4).`in`