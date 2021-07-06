package com.github.pambrose

import io.kvision.annotations.KVService
import kotlinx.serialization.Serializable

@KVService
interface IContentService {
  suspend fun content(title: String): String
  suspend fun choices(title: String): List<ChoiceTitle>
  suspend fun choiceOrientation(title: String): ChoiceOrientation
}

@Serializable
class ChoiceTitle(val choice: String, val title: String)

@Serializable
enum class ChoiceOrientation { VERTICAL, HORIZONTAL }