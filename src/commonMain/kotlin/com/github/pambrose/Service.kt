package com.github.pambrose

import io.kvision.annotations.KVService
import kotlinx.serialization.Serializable

@KVService
interface IContentService {
  suspend fun title(title: String): String
  suspend fun content(title: String): String
  suspend fun choices(title: String): List<ChoiceTitle>
  suspend fun choiceOrientation(title: String): ChoiceOrientation
  suspend fun parentTitles(title: String): List<String>
}

@KVService
interface IUserService {
  suspend fun choose(user: String, fromTitle: String, toTitle: String, choice: String, reason: String): String
}

@Serializable
class ChoiceTitle(val choice: String, val title: String)

@Serializable
enum class ChoiceOrientation { VERTICAL, HORIZONTAL }