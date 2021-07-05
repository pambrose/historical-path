package com.github.pambrose

import com.github.pambrose.PointInTime.Companion.allNodes

actual class ContentService : IContentService {
  init {
    println("Created ContentService")
  }

  override suspend fun content(title: String): String {
    val node = if (title == "/") rootPointInTime else allNodes[title] ?: error("Invalid title: $title")
    return MarkdownParser.toHtml(node.content)
  }

  override suspend fun choices(title: String): List<ChoiceTitle> {
    val node = if (title == "/") rootPointInTime else allNodes[title] ?: error("Invalid title: $title")
    return node.childen.map { (choice, pit) -> ChoiceTitle(choice, pit.title) }
  }

  companion object {
    var cnt = 0

    val rootPointInTime =
      PointInTime(
        "Season to Leave",
        """
                    <h1>Straight HTML</h1>
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
}
