package app

import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.websocket.WebSockets

fun main() {
    embeddedServer(Netty, port = 8181) {
        install(WebSockets)
        endpoints()
    }.start(wait = true)
}