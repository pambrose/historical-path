package com.github.pambrose

object Model {
  private val contentService = ContentService()
  suspend fun title(title: String) = contentService.title(title)
  suspend fun content(title: String) = contentService.content(title)
  suspend fun choices(title: String) = contentService.choices(title)
  suspend fun choiceOrientation(title: String) = contentService.choiceOrientation(title)
  suspend fun parentTitles(title: String) = contentService.parentTitles(title)
  suspend fun currentScore() = contentService.currentScore()

  private val userService = UserService()
  suspend fun choose(user: String, fromTitle: String, toTitle: String, choice: String, reason: String) =
    userService.choose(user, fromTitle, toTitle, choice, reason)
}