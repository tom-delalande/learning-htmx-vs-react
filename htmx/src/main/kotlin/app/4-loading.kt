package app

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.main
import kotlinx.html.p
import kotlinx.html.style

fun Routing.loading() {
    get("/content/loading") {
        call.respondHtml {
            body {
                loading()
            }
        }
    }

    var percentage = 0
    get("/content/loading/status") {
        call.respondHtml {
            body {
                percentage += 5
                if (percentage > 100) percentage = 0

                // Return updated loading bar
                div {
                    id = "loading-bar"

                    attributes["hx-get"] = "/content/loading/status"
                    attributes["hx-trigger"] = "load delay:300ms"
                    attributes["hx-swap"] = "outerHTML"

                    classes = setOf("bg-blue-300", "h-32", "rounded-md", "transition-all")
                    style = "width: $percentage%"
                }
            }
        }
    }
}

private fun FlowContent.loading() = div {
    header {
        classes = setOf("bg-white", "shadow")
        div {
            classes = setOf("mx-auto", "max-w-7xl", "px-4", "py-6", "sm:px-6", "lg:px-8")
            h1 {
                classes = setOf("text-3xl", "font-bold", "tracking-tight", "text-gray-900")
                +"Loading"
            }
        }
    }
    main {
        classes = setOf("mx-auto", "max-w-7xl", "py-6", "sm:px-6", "lg:px-8", "flex", "gap-4", "flex-col")
        p { +"Progress..." }
        div {
            // Poll endpoint, which updates itself with the response
            id = "loading-bar"
            attributes["hx-get"] = "/content/loading/status"
            attributes["hx-trigger"] = "load delay:300ms"
            attributes["hx-swap"] = "outerHTML"

            classes = setOf("bg-blue-300", "h-32", "rounded-md", "transition-all")
            style = "width: 0%"
        }
    }
}