package com.github.pambrose

import io.kvision.annotations.KVService
import kotlinx.serialization.Serializable

@KVService
interface IContentService {
  suspend fun content(title: String): String
  suspend fun choices(title: String): List<ChoiceTitle>
}

@Serializable
class ChoiceTitle(val choice: String, val title: String)