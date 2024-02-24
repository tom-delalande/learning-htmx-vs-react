package app

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.script

fun Application.endpoints() {
    routing {
        get {
            call.respondHtml {
                head {
                    script {
                        src = "https://unpkg.com/htmx.org@1.9.10"
                    }
                    script {
                        src = "https://cdn.tailwindcss.com"
                    }
                }
                body {
                    navigation()
                }
            }
        }

        tabs()
        multiSelect()
        animation()
        loading()
        chat()
    }
}
