package com.github.pambrose

import com.github.pambrose.Slide.Companion.slide

object Content {
  fun initContent() {
    slide("Season to Leave") {
      content =
        """
            <h1>Welcome to this lesson</h1>
            ## I am a header 
            ### I am a smaller header 
            
            Here are some bullet points:
            * Item 1 with *italics*
            * Item 2 with **bold**
            
            When will you choose to leave?
        """

      choice("Spring", "Spring Choice")
      choice("Summer", "Summer Choice")
      choice("Fall", "Fall Choice")
      choice("Winter", "Winter Choice")
    }

    slide("Spring Choice") {
      content = """
        # This is the spring choice slide
        
        Here are the choices of clothing:
      """
      choiceOrientation = ChoiceOrientation.HORIZONTAL

      choice("Clothing Choice 1", "Warm Jacket1")
      choice("Clothing Choice 2", "Warm Jacket2")
      choice("Clothing Choice 3", "Warm Jacket3")
    }

    slide("Summer Choice") {
      content = "Summer Slide"
    }

    slide("Fall Choice") {
      content = "Fall Slide"
    }

    slide("Winter Choice") {
      content = "Winter Slide"
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