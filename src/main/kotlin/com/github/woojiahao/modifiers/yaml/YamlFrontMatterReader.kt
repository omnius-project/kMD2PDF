package com.github.woojiahao.modifiers.yaml

import com.github.woojiahao.utility.extensions.getOrNull

fun parseYaml(yaml: Map<String?, List<String>?>) =
  YamlConfiguration(
    yaml.getOrNull("font"),
    yaml.getOrNull("monospaceFont"),
    yaml.getOrNull("fontSize")?.first()?.toInt(),
    yaml.getOrNull("theme")?.first()
  )

