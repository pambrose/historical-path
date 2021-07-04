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

fun main() {
    val rootPointInTime =
        PointInTime(
            "Season to Leave",
            """
            # I am a header 
            ## I am a header 
            ### I am a header 
            *Italics* **bold**
        """,
            "Spring" to PointInTime("Spring Choice", "Description for Spring"),
            "Summer" to PointInTime("Summer Choice", "Description for Summer"),
            "Fall" to PointInTime("Fall Choice", "Description for Fall"),
            "Winter" to PointInTime("Winter Choice", "Description for Winter"),
        )

}