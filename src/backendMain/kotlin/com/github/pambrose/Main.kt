package com.github.pambrose

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit

data class UserSession(val id: String, val count: Int)

fun Application.main() {
  Content.initContent()
  Slide.verifySlides()

  install(Compression)

  install(Sessions) {
    cookie<UserSession>("user_session") {
      cookie.path = "/"
      cookie.maxAgeInSeconds = 60
    }
  }

  routing {
    applyRoutes(ContentServiceManager)

    get("/here") {
      call.respondText("I am here.")
    }

    get("/doit") {
      call.respondRedirect("/play")
    }
  }
  kvisionInit()
}
