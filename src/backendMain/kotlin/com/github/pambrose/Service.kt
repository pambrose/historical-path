package com.github.pambrose

import com.github.pambrose.Slide.Companion.findSlide
import com.github.pambrose.Slide.Companion.slide
import com.github.pambrose.Slide.Companion.verifySlides

actual class ContentService : IContentService {
  init {
    println("Created ContentService")
  }

  override suspend fun content(title: String) = MarkdownParser.toHtml(findSlide(title).content)

  override suspend fun choices(title: String) =
    findSlide(title).choices.map { (choice, destination) -> ChoiceTitle(choice, destination) }

  companion object {
    val rootSlide = "Season to Leave"

    init {
      slide(
        "Season to Leave",
        """
            <h1>Straight HTML</h1>
            # I am a header 
            ## I am a header 
            ### I am a header 
            *Italics* **bold**
        """
      ) {
        choice("Spring", "SpringClothing Choice")
        choice("Summer", "Description for Summer")
        choice("Fall", "Description for Fall")
        choice("Winter", "Description for Winter")
      }

      slide("Description for Summer", "Description for Summer")
      slide("Description for Fall", "Description for Fall")
      slide("Description for Winter", "Description for Winter")

      slide("SpringClothing Choice", """# I am in Spring Choice""") {
        choice("Clothing Choice 1", "Warm Jacket1")
        choice("Clothing Choice 2", "Warm Jacket2")
        choice("Clothing Choice 3", "Warm Jacket3")
      }

      slide("Warm Jacket1", "Description for Warm Jacket1")
      slide("Warm Jacket2", "Description for Warm Jacket2")
      slide("Warm Jacket3", "Description for Warm Jacket3")

      verifySlides()
    }
  }
}
