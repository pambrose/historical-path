package com.github.pambrose

import com.github.pambrose.Slide.Companion.findSlide
import com.github.pambrose.Slide.Companion.slide
import com.github.pambrose.Slide.Companion.verifySlides
import org.jetbrains.annotations.NotNull

actual class ContentService : IContentService {
  init {
    //println("Created ContentService")
  }

  override suspend fun content(title: String): @NotNull String {
    val escaped = findSlide(title).content
      .replace("<", "---LT---")
      .replace(">", "---GT---")
    return MarkdownParser.toHtml(escaped)
      .replace("---LT---", "<")
      .replace("---GT---", ">").also {
        println("Returning $it")
      }
  }

  override suspend fun choices(title: String) =
    findSlide(title).choices.map { (choice, destination) -> ChoiceTitle(choice, destination) }

  companion object {
    val rootSlide = "Season to Leave"

    init {
      slide("Season to Leave") {
        content =
          """
            <h1>Straight HTML</h1>
            # I am a header 
            ## I am a header 
            ### I am a header 
            *Italics* **bold**
        """

        choice("Spring", "SpringClothing Choice")
        choice("Summer", "Description for Summer")
        choice("Fall", "Description for Fall")
        choice("Winter", "Description for Winter")
      }

      slide("SpringClothing Choice") {
        content = """# I am in Spring Choice"""
        choice("Clothing Choice 1", "Warm Jacket1")
        choice("Clothing Choice 2", "Warm Jacket2")
        choice("Clothing Choice 3", "Warm Jacket3")
      }

      slide("Description for Summer") {
        content = "Description for Summer"
      }

      slide("Description for Fall") {
        content = "Description for Fall"
      }

      slide("Description for Winter") {
        content = "Description for Winter"
      }

      slide("Warm Jacket1") {
        content = "Description for Warm Jacket1"
      }
      slide("Warm Jacket2") {
        content = "Description for Warm Jacket2"
      }
      slide("Warm Jacket3") {
        content = "Description for Warm Jacket3"
      }

      verifySlides()
    }
  }
}
