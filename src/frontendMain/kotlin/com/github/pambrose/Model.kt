package com.github.pambrose

object Model {
  private val contentService = ContentService()
  suspend fun content(title: String) = contentService.content(title)
  suspend fun choices(title: String) = contentService.choices(title)
  suspend fun choiceOrientation(title: String) = contentService.choiceOrientation(title)
}