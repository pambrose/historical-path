package com.github.pambrose

import com.github.pambrose.Slide.Companion.findSlide
import org.jetbrains.annotations.NotNull

actual class ContentService : IContentService {
  init {
    //println("Created ContentService")
  }

  override suspend fun content(title: String): @NotNull String {
    val escaped = findSlide(title).content
      .replace("<", ltEscape)
      .replace(">", gtEscape)
    return MarkdownParser.toHtml(escaped)
      .replace(ltEscape, "<")
      .replace(gtEscape, ">").also {
        println("Returning $it")
      }
  }

  override suspend fun choices(title: String) =
    findSlide(title).choices.map { (choice, destination) -> ChoiceTitle(choice, destination) }

  override suspend fun choiceOrientation(title: String): @NotNull ChoiceOrientation =
    findSlide(title).choiceOrientation

  override suspend fun parentTitles(title: String): List<String> {
    val parentTitles = mutableListOf<String>()
    var currSlide = findSlide(title).parentSlide
    while (currSlide != null) {
      parentTitles += currSlide.title
      currSlide = currSlide.parentSlide
    }
    return parentTitles
  }

  companion object {
    val ltEscape = "---LT---"
    val gtEscape = "---GT---"
  }
}
