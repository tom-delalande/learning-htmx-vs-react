import React, { useEffect, useState } from "react";

export default function Loading() {
  return (
    <>
      <header className="bg-white shadow">
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
          <h1 className="text-3xl font-bold tracking-tight text-gray-900">
            Asynchronous Loading
          </h1>
        </div>
      </header>
      <main>
        <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8 flex flex-col gap-4">
          <Content />
        </div>
      </main>
    </>
  );
}

function Content() {
  // Keep percentage state
  const [percentage, setPercentage] = useState(0);

  useEffect(() => {
    // This would usually be a polling network request
    const interval = setInterval(() => {
      new setPercentage((percentage + 5) % 100);
    }, 300);
    return () => {
      clearInterval(interval);
    };
  });
  return (
    <>
      <p>Progress...</p>
      {/* Update percentage */}
      <div
        className="bg-blue-300 h-32 rounded-md transition-all"
        style={{ width: percentage + "%" }}
      ></div>
    </>
  );
}
