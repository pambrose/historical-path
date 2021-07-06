package com.github.pambrose

import com.github.pambrose.Slide.Companion.slide

object Slides {
  fun initData() {
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
      choiceOrientation = ChoiceOrientation.HORIZONTAL

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

      choice("Red Jacket", "Red Jacket")
      choice("Blue Jacket", "Blue Jacket")
    }

    slide("Red Jacket") {
      content = "Description for Red Jacket"
    }

    slide("Blue Jacket") {
      content = "Description for Blue Jacket"
    }

    slide("Warm Jacket2") {
      content = "Description for Warm Jacket2"
    }

    slide("Warm Jacket3") {
      content = "Description for Warm Jacket3"
    }
  }
}