package app

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import javax.management.monitor.StringMonitor
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.li
import kotlinx.html.main
import kotlinx.html.p
import kotlinx.html.role
import kotlinx.html.time
import kotlinx.html.ul

fun Routing.multiSelect() {
    get("/content/multi-select") {
        call.respondHtml {
            body {
                multiselect()
            }
        }
    }

    post("/content/multi-select/table/{attribute}/{enabled}") {
        val attribute = call.parameters["attribute"]
        val enabled = call.parameters["enabled"].toBoolean()
        val parameters = call.receiveParameters()

        val name = if (attribute == "name") !enabled else parameters["name"].toBoolean()
        val email = if (attribute == "email") !enabled else parameters["email"].toBoolean()
        val lastOnline = if (attribute == "last-online") !enabled else parameters["last-online"].toBoolean()

        call.respondHtml {
            body {
                multiselect(name, email, lastOnline)
            }
        }
    }
}

private data class Person(
    val name: String,
    val email: String,
    val role: String,
    val imageUrl: String,
    val lastSeen: String?,
    val lastSeenDateTime: String? = null,
)

fun FlowContent.multiselect(
    nameEnabled: Boolean = true,
    emailEnabled: Boolean = true,
    lastOnlineEnabled: Boolean = true,
) = div {
    // List of people
    val people = listOf(
        Person(
            "Leslie Alexander",
            "leslie.alexander@example.com",
            "Co-Founder / CEO",
            "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
            "3h ago",
            "2023-01-23T13:23Z",
        ),
        Person(
            "Michael Foster",
            "michael.foster@example.com",
            "Co-Founder / CTO",

            "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
            "3h ago",
            "2023-01-23T13:23Z",
        ),
        Person(
            "Dries Vincent",
            "dries.vincent@example.com",
            "Business Relations",

            "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
            null,
        ),
        Person(
            "Lindsay Walton",
            "lindsay.walton@example.com",
            "Front-end Developer",

            "https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
            "3h ago",
            "2023-01-23T13:23Z",
        ),
        Person(
            "Courtney Henry",
            "courtney.henry@example.com",
            "Designer",

            "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
            "3h ago",
            "2023-01-23T13:23Z",
        ),
        Person(
            "Tom Cook",
            "tom.cook@example.com",
            "Director of Product",

            "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
            null,
        )
    )

    header {
        classes = setOf("bg-white", "shadow")
        div {
            classes = setOf("mx-auto", "max-w-7xl", "px-4", "py-6", "sm:px-6", "lg:px-8")
            h1 {
                classes = setOf("text-3xl", "font-bold", "tracking-tight", "text-gray-900")
                +"Input updates a separate component"
            }
        }
    }
    main {
        div {
            classes = setOf("mx-auto", "max-w-7xl", "py-6", "sm:px-6", "lg:px-8")
            // Inputs post a form request with the updated values
            // Response updates the whole content div
            form {
                classes = setOf("max-w-sm", "mx-auto", "my-4", "gap-2")

                input {
                    attributes["hx-post"] = "/content/multi-select/table/name/${nameEnabled}"
                    attributes["hx-target"] = "#content"

                    id = "name"
                    type = InputType.checkBox
                    name = "name"
                    value = nameEnabled.toString()
                    checked = nameEnabled
                }
                label {
                    htmlFor = "multi-select"
                    +" Name"
                }
                br { }
                input {
                    attributes["hx-post"] = "/content/multi-select/table/email/${emailEnabled}"
                    attributes["hx-target"] = "#content"

                    id = "email"
                    type = InputType.checkBox
                    name = "email"
                    value = emailEnabled.toString()
                    checked = emailEnabled
                }
                label {
                    htmlFor = "email"
                    +" Email"
                }
                br { }
                input {
                    attributes["hx-post"] = "/content/multi-select/table/last-online/${lastOnlineEnabled}"
                    attributes["hx-target"] = "#content"

                    id = "last-online"
                    type = InputType.checkBox
                    name = "last-online"
                    value = lastOnlineEnabled.toString()
                    checked = lastOnlineEnabled
                }
                label {
                    htmlFor = "last-online"
                    +" Last Online"
                }
                br { }
            }
            div {
                ul {
                    role = "list"
                    classes = setOf("divide-y", "divide-gray-100")
                    people.map {
                        li {
                            classes = setOf("flex", "justify-between", "gap-x-6", "py-5")
                            div {
                                classes = setOf("flex", "min-w-0", "gap-x-4")
                                img {
                                    classes = setOf("h-12", "w-12", "flex-none", "rounded-full", "bg-gray-50")
                                    src = it.imageUrl
                                    alt = ""
                                }
                                div {
                                    classes = setOf("min-w-0", "flex-auto")
                                    // Conditional rendering
                                    if (nameEnabled) {
                                        p {
                                            classes = setOf("text-sm", "font-semibold", "leading-6", "text-gray-900")
                                            +it.name
                                        }
                                    }
                                    if (emailEnabled) {
                                        p {
                                            classes = setOf("mt-1", "truncate", "text-xs", "leading-5", "text-gray-500")
                                            +it.email
                                        }

                                    }
                                }
                            }
                            div {
                                classes = setOf("hidden", "shrink-0", "sm:flex", "sm:flex-col", "sm:items-end")
                                p {
                                    classes = setOf("text-sm", "leading-6", "text-gray-900")
                                    +it.role
                                }
                                if (lastOnlineEnabled) {
                                    if (it.lastSeenDateTime != null && it.lastSeen != null) {
                                        p {
                                            classes = setOf("mt-1", "text-xs", "leading-5", "text-gray-500")
                                            +"Last seen "
                                            time {
                                                attributes["datetime"] = it.lastSeenDateTime
                                                +it.lastSeen
                                            }
                                        }
                                    } else {
                                        div {
                                            classes = setOf("mt-1", "flex", "items-center", "gap-x-1.5")
                                            div {
                                                classes = setOf("flex-none", "rounded-full", "bg-emerald-500/20", "p-1")
                                                div {
                                                    classes = setOf("h-1.5", "w-1.5", "rounded-full", "bg-emerald-500")
                                                }
                                            }
                                            p {
                                                classes = setOf("text-xs", "leading-5", "text-gray-500")
                                                +"Online"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
