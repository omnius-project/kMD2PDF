package com.github.woojiahao.toc

class TableOfContents(private val settings: TableOfContentsSettings) {

  private val topLevel = Level(0, "")

  fun addLevel(level: Level) = topLevel.addSubLevel(level)

  fun print() {
    val levelInformation = topLevel.printLevel(topLevel, settings.nestedLevel)
    println(levelInformation)
  }

  class Level(private val level: Int, private val content: String) {
    private var parentLevel: Level? = null
    private val subLevels = mutableListOf<Level>()
    private var observedLevel: Level? = null

    fun printLevel(level: Level, nestedDepth: Int): List<String> {
      val levelInformation = mutableListOf<String>()
      if (level.level != 0 && level.level <= nestedDepth) {
        println(level)
        levelInformation += level.toString()
      }
      for (childLevel in level.subLevels) printLevel(childLevel, nestedDepth)

      return levelInformation.toList()
    }

    fun addSubLevel(subLevel: Level) {
      if (observedLevel == null || subLevel.level <= observedLevel!!.level) {
        // Adding to the current level's subLevel pool
        subLevels += subLevel
        observedLevel = subLevel
        subLevel.parentLevel = this
      } else {
        // Digging deeper into the observedLevel pool
        observedLevel!!.addSubLevel(subLevel)
      }
    }

    override fun toString(): String {
      val indent = "\t".repeat(level - 1)
      return "$indent$level. $content"
    }
  }
}