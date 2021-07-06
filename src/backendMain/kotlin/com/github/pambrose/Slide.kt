package com.github.pambrose

class Slide(val title: String) {
  var parentSlide: Slide? = null
  val choices = mutableMapOf<String, String>()
  var content: String = ""
  var choiceOrientation: ChoiceOrientation = ChoiceOrientation.VERTICAL

  init {
    require(title !in allSlides.keys) { "Slide titles must be unique: $title" }
    allSlides[title] = this
  }

  fun choice(choice: String, destination: String) {
    choices[choice] = destination
  }

  companion object {
    val allSlides = mutableMapOf<String, Slide>()

    fun slide(title: String, block: Slide.() -> Unit = { }) {
      Slide(title).block()
    }

    fun findSlide(title: String) =
      (if (title == "/") allSlides[ContentService.rootSlide] else allSlides[title]) ?: error("Invalid title: $title")

    fun verifySlides() {
      allSlides.forEach { title, slide ->
        slide.choices.forEach { choice, destination ->
          val destSlide = allSlides[destination] ?: throw IllegalArgumentException("Invalid slide title: $destination")
          if (destSlide.parentSlide != null)
            throw IllegalArgumentException("Parent slide already assigned to : $destination")
          destSlide.parentSlide = slide
        }
      }
    }
  }
}