import { Disclosure } from "@headlessui/react";
import Empty from "./1-tab";
import React, { useState } from "react";
import Chat from "./5-chat";
import Loading from "./4-loading";
import Animation from "./3-animation";
import Multi from "./2-multi";

// List of tabs

const navigation = [
  { name: "Tabs" },
  { name: "Input updates a seperate component" },
  { name: "Animation" },
  { name: "Asynchronous Loading" },
  { name: "Chat" },
];

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export default function Index() {
  // Store the currrent page name
  const [page, setPage] = useState("Tabs");
  return (
    <>
      <div className="min-h-full">
        <Disclosure as="nav" className="bg-gray-800">
          {({ open }) => (
            <>
              <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                <div className="flex h-16 items-center justify-between">
                  <div className="flex items-center">
                    <div className="flex-shrink-0">
                      <img
                        className="h-8 w-8"
                        src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=500"
                        alt="Your Company"
                      />
                    </div>
                    <div className="hidden md:block">
                      <div className="ml-10 flex items-baseline space-x-4">
                        {/* Update page name when clicked */}
                        {navigation.map((item) => (
                          <a
                            key={item.name}
                            href={item.href}
                            className={classNames(
                              "text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium",
                            )}
                            onClick={() => setPage(item.name)}
                          >
                            {item.name}
                          </a>
                        ))}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </>
          )}
        </Disclosure>
        {/* Render the component for whatever the current page is */}
        {page === "Tabs" ? <Empty /> : <></>}
        {page === "Input updates a seperate component" ? <Multi /> : <></>}
        {page === "Animation" ? <Animation /> : <></>}
        {page === "Asynchronous Loading" ? <Loading /> : <></>}
        {page === "Chat" ? <Chat /> : <></>}
      </div>
    </>
  );
}
