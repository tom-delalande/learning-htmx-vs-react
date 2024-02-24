import React, { useState } from "react";

export default function Animation() {
  return (
    <>
      <header className="bg-white shadow">
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
          <h1 className="text-3xl font-bold tracking-tight text-gray-900">
            Animation
          </h1>
        </div>
      </header>
      <main>
        <div>
          <Content />
        </div>
      </main>
    </>
  );
}

function Content() {
  // Keep shape state
  const [shape, setShape] = useState("circle");

  return (
    <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8 flex gap-4 flex-col">
      {/* Conditionally render the shape */}
      <div
        class={
          shape === "circle"
            ? "rounded-full w-32 h-32 bg-red-600 transition-all duration-500"
            : "w-full h-32 bg-blue-600 rounded-md transition-all duration-500"
        }
      ></div>
      {/* Button updates shape */}
      <button
        type="button"
        class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
        onClick={() =>
          shape === "circle" ? setShape("rectangle") : setShape("circle")
        }
      >
        Click Me
      </button>
    </div>
  );
}
