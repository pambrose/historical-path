package com.github.pambrose

object Model {
    private val pingService = PingService()
    suspend fun ping(message: String) = pingService.ping(message)
}