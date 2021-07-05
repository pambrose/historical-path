package com.github.pambrose

class PointInTime(val title: String, val content: String, vararg choices: Pair<String, PointInTime>) {
  val childen = mutableListOf<Pair<String, PointInTime>>()

  init {
    require(title !in allNodes.keys) { "Titles must be unique" }
    allNodes[title] = this
    choices.forEach {
      childen += it
    }
  }

  companion object {
    val allNodes = mutableMapOf<String, PointInTime>()
  }
}