package com.github.pambrose

import io.kvision.annotations.KVService

@KVService
interface IPingService {
    suspend fun ping(message: String): String
}
