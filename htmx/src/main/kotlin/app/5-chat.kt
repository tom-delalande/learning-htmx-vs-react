package app

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.websocket.webSocket
import io.ktor.utils.io.core.buildPacket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlin.random.Random
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.main
import kotlinx.html.p
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import util.with
import util.withHTML

fun Routing.chat() {
    get("/content/chat") {
        call.respondHtml {
            body {
                chat()
            }
        }
    }
    @Serializable
    data class Payload(val message: String)

    val json = Json {
        ignoreUnknownKeys = true
    }

    data class Message(
        val message: String,
        val sent: Boolean,
    )
    webSocket("/content/chat/ws") {
        val messages = mutableListOf<Message>()
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val data = frame.readText()
            val payload = json.decodeFromString<Payload>(data)
            val output =
                payload.message.map { if (Random.nextBoolean()) it.uppercase() else it.lowercase() }.joinToString("")
            messages += Message(payload.message, true)
            messages += Message(output, false)
            val response = buildPacket {
                // Websocket replies with updated list of messages
                withHTML().with {
                    div {
                        id = "messages"
                        messages.map {
                            p {
                                classes = setOf(
                                    "rounded-md",
                                    "my-2",
                                    "w-full",
                                    "px-4",
                                    "py-2"
                                ) + if (it.sent) "bg-blue-200" else "bg-neutral-200"
                                +it.message
                            }
                        }
                    }
                    input {
                        id = "input"
                        name = "message"
                        type = InputType.text
                        classes = setOf("w-full", "h-8", "border-2", "border-black")
                        value = ""
                        autoFocus = true
                    }
                }
            }.readText()
            send(Frame.Text(response))
        }
    }
}

private fun FlowContent.chat() = div {
    header {
        classes = setOf("bg-white", "shadow")
        div {
            classes = setOf("mx-auto", "max-w-7xl", "px-4", "py-6", "sm:px-6", "lg:px-8")
            h1 {
                classes = setOf("text-3xl", "font-bold", "tracking-tight", "text-gray-900")
                +"Chat"
            }
        }
    }
    main {
        div {
            attributes["hx-ws"] = "connect:/content/chat/ws"
            classes = setOf("mx-auto", "max-w-7xl", "py-6", "sm:px-6", "lg:px-8")

            // This div matches the id of the div returned by the
            // Websocket, so it will be replaced

            div { id = "messages" }
            // Input submits form as JSON to the websocket
            form {
                attributes["hx-ws"] = "send:submit"
                input {
                    id = "input"
                    name = "message"
                    type = InputType.text
                    classes = setOf("w-full", "h-8", "border-2", "border-neutral-200", "rounded-md")
                }
            }
        }
    }

}