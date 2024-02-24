package app

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlin.collections.set
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.main
import kotlinx.html.nav

fun Routing.tabs() {
    get("/content/empty") {
        call.respondHtml {
            body {
                empty()
            }
        }
    }
}

// Empty First Page

fun FlowContent.empty() = div {
    header {
        classes = setOf("bg-white", "shadow")
        div {
            classes = setOf("mx-auto", "max-w-7xl", "px-4", "py-6", "sm:px-6", "lg:px-8")
            h1 {
                classes = setOf("text-3xl", "font-bold", "tracking-tight", "text-gray-900")
                +"Tabs"
            }
        }
    }
    main {
        div {
            classes = setOf("mx-auto", "max-w-7xl", "py-6", "sm:px-6", "lg:px-8")
        }
    }
}

private data class Tab(
    val name: String,
    val url: String,
)

// Navigation

fun FlowContent.navigation() {
    // List of tabs
    val tabs = listOf(
        Tab("Tabs", "/content/empty"),
        Tab("Input updates a separate component", "/content/multi-select"),
        Tab("Animation", "/content/animation"),
        Tab("Asynchronous Loading", "/content/loading"),
        Tab("Chat", "/content/chat"),
    )
    div {
        classes = setOf("min-h-full")
        nav {
            classes = setOf("bg-gray-800")
            div {
                classes = setOf("mx-auto", "max-w-7xl", "px-4", "sm:px-6", "lg:px-8")
                div {
                    classes = setOf("flex", "h-16", "items-center", "justify-between")
                    div {
                        classes = setOf("flex", "items-center")
                        div {
                            classes = setOf("flex-shrink-0")
                            img {
                                classes = setOf("h-8", "w-8")
                                src = "https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=500"
                                alt = "Your Company"
                            }
                        }
                        div {
                            classes = setOf("hidden", "md:block")
                            div {
                                classes = setOf("ml-10", "flex", "items-baseline", "space-x-4")
                                tabs.map {
                                    a {
                                        // Fetch from the URL and update the content div
                                        attributes["hx-get"] = it.url
                                        attributes["hx-target"] = "#content"

                                        classes = setOf(
                                            "text-gray-300",
                                            "hover:bg-gray-700",
                                            "hover:text-white",
                                            "rounded-md",
                                            "px-3",
                                            "py-2",
                                            "text-sm",
                                            "font-medium"
                                        )
                                        +it.name
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        div {
            // ID of "content" is used to switch the page
            id = "content"
            empty()
        }
    }
}
