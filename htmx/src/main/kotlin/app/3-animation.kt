package app

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.main

fun Routing.animation() {
    get("/content/animation") {
        call.respondHtml {
            body {
                animation("circle")
            }
        }
    }
    get("/content/animation/{shape}") {
        val shape = call.parameters["shape"] ?: "circle"
        call.respondHtml {
            body {
                animation(shape)
            }
        }
    }
}

private fun FlowContent.animation(shape: String) = div {
    header {
        classes = setOf("bg-white", "shadow")
        div {
            classes = setOf("mx-auto", "max-w-7xl", "px-4", "py-6", "sm:px-6", "lg:px-8")
            h1 {
                classes = setOf("text-3xl", "font-bold", "tracking-tight", "text-gray-900")
                +"Animation"
            }
        }
    }
    main {
        classes = setOf("mx-auto", "max-w-7xl", "py-6", "sm:px-6", "lg:px-8", "flex", "gap-4", "flex-col")
        // Render whichever shape is selected
        // CSS animations apply as normal
        // But id must be consistent
        if (shape == "circle") {
            div {
                id = "animation-object"
                classes = setOf("rounded-full", "w-32", "h-32", "bg-red-600", "transition-all", "duration-500")
            }
        } else {
            div {
                id = "animation-object"
                classes = setOf("w-full", "h-32", "bg-blue-600", "rounded-md", "transition-all", "duration-500")
            }
        }
        button {
            // Button makes a request for the other shape
            // Replaces the whole div
            val otherShape = if (shape == "circle") "rectangle" else "circle"
            attributes["hx-get"] = "/content/animation/$otherShape"
            attributes["hx-target"] = "#content"

            type = ButtonType.button
            classes = setOf(
                "text-white",
                "bg-blue-700",
                "hover:bg-blue-800",
                "focus:ring-4",
                "focus:ring-blue-300",
                "font-medium",
                "rounded-lg",
                "text-sm",
                "px-5",
                "py-2.5",
                "me-2",
                "mb-2",
                "dark:bg-blue-600",
                "dark:hover:bg-blue-700",
                "focus:outline-none",
                "dark:focus:ring-blue-800"
            )
            +"Click Me"
        }
    }
}
