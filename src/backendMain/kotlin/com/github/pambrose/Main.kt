package com.github.pambrose

import com.github.pambrose.common.response.respondWith
import com.github.pambrose.common.util.isNull
import com.github.pambrose.common.util.randomId
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit

data class UserSession(val id: String)

internal val ApplicationCall.browserSession get() = sessions.get<UserSession>()

fun Application.main() {
  Content.initContent()
  Slide.verifySlides()

  install(DefaultHeaders)
  install(CallLogging)
  install(Compression)

  install(Sessions) {
    cookie<UserSession>("user_session") {
      cookie.path = "/"
      cookie.maxAgeInSeconds = 60
      cookie.httpOnly = true
      cookie.extensions["SameSite"] = "lax"
    }
  }

//  install(Sessions) {
//    cookie<Profile>("SESSION", storage = SessionStorageMemory()) {
//      cookie.path = "/"
//      cookie.extensions["SameSite"] = "strict"
//    }
//  }
  //Db.init(environment.config)

  install(Authentication) {
    form {
      userParamName = "username"
      passwordParamName = "password"
      //validate { credentials ->
//        dbQuery {
//          UserDao.select {
//            (UserDao.username eq credentials.name) and (UserDao.password eq DigestUtils.sha256Hex(
//              credentials.password
//            ))
//          }.firstOrNull()?.let {
//            UserIdPrincipal(credentials.name)
//          }
//        }
      //    }
      //    skipWhen { call -> call.sessions.get<Profile>() != null }
    }
  }

  routing {
    applyRoutes(ContentServiceManager)
    applyRoutes(UserServiceManager)

    get("/here") {
      respondWith {
        if (call.browserSession.isNull()) {
          val browserSession = UserSession(id = randomId(15))
          call.sessions.set(browserSession)
        }
        "I am here."
      }
    }

    get("/doit") {
      call.respondRedirect("/play")
    }
  }

  routing {
    //applyRoutes(RegisterProfileServiceManager) // No authentication needed
    authenticate {
      post("login") {
        // ...
      }
      get("logout") {
        //call.sessions.clear<Profile>()
        call.respondRedirect("/")
      }
      //applyRoutes(AddressServiceManager) // Authentication needed
      //applyRoutes(ProfileServiceManager) // Authentication needed
    }
  }

  kvisionInit()
}