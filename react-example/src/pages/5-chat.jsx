import React, { useState } from "react";

export default function Chat() {
  return (
    <>
      <header className="bg-white shadow">
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
          <h1 className="text-3xl font-bold tracking-tight text-gray-900">
            Chat
          </h1>
        </div>
      </header>
      <main>
        <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
          <Content />
        </div>
      </main>
    </>
  );
}

function Content() {
  // Keep state for messages and current input value
  const [messages, setMessages] = useState([]);
  const [value, setValue] = useState("");

  return (
    <div>
      {/* Render messages */}
      {messages.map((message) => {
        return (
          <p
            class={`rounded-md my-2 w-full px-4 py-2 ${
              message.sent ? "bg-blue-200" : "bg-neutral-200"
            }`}
          >
            {message.message}
          </p>
        );
      })}
      <input
        id="input"
        name="message"
        type="text"
        class="w-full h-8 border-2 border-neutral-200 rounded-md"
        value={value}
        onChange={(event) => setValue(event.target.value)}
        onKeyDown={(event) => {
          if (event.key === "Enter") {
            // Append message and response
            // This would be done via a websocket
            setMessages([
              ...messages,
              {
                sent: true,
                message: value,
              },
              {
                sent: false,
                message: Array.from(value)
                  .map((it) =>
                    Math.random() > 0.5 ? it.toUpperCase() : it.toLowerCase(),
                  )
                  .join(""),
              },
            ]);
            setValue("");
          }
        }}
      />
    </div>
  );
}
