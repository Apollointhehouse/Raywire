# Raywire

Raywire is an event library for Better Than Adventure (BTA), designed to provide a flexible and efficient event-driven system for BTA mod developers.

Written in Kotlin, Raywire makes it easy to create, subscribe to, and manage custom events within your BTA mods.

## Features

- Simple and extensible event bus system
- Annotation-based event listeners
- Priority-based event handling
- Cancellable and modifiable events
- Kotlin-first API, fully interoperable with Java
- Lightweight and fast

## Prerequisites

- JDK 21 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community Edition is sufficient)
- Minecraft Development plugin (optional, but recommended)
- BTA modding environment set up

## Using Raywire in Your Mod

Add Raywire as a dependency in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Apollointhehouse:Raywire:<version>")
}
```

Replace `<version>` with the latest Raywire release.

---

# Basic Usage

## Defining an event

```kotlin
import me.apollointhehouse.raywire.api.Event

class FooEvent(val bar: String) : Event
```

## Creating a listener

```kotlin
import me.apollointhehouse.raywire.api.EventHandler

class FooListener {

    @EventHandler
    fun onTick(event: TickEvent.Post) {
        println("Tick event!")
    }

    @EventHandler
    fun onFooEvent(event: FooEvent) {
        println("Foo event with bar: ${event.bar}")
    }
}
```

## Creating an event bus

```kotlin
import me.apollointhehouse.raywire.api.Bus

val bus = Bus()
```

Or use the global bus:

```kotlin
import me.apollointhehouse.raywire.Raywire

val bus = Raywire.globalBus
```

## Subscribing a listener

```kotlin
bus.subscribe(FooListener())
```

## Posting an event

```kotlin
bus.post(FooEvent("hello"))
```

By default, this invokes **all handlers**, regardless of cancellation.

---

# Cancellable Events

Raywire supports cancellable events through the `Cancellable` interface:

```kotlin
import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.raywire.api.Cancellable

class DamageEvent(
    val target: String,
    var amount: Double
) : Event, Cancellable by Cancellable()
```

### Cancelling an event in a handler

```kotlin
class DamageListener {

    @EventHandler(priority = 100)
    fun onHighPriority(event: DamageEvent) {
        if (event.amount >= 9000.0) {
            println("Preventing absurd damage to ${event.target}")
            event.cancel()
        }
    }

    @EventHandler(priority = 0)
    fun onNormalDamage(event: DamageEvent) {
        println("Applying damage to ${event.target}")
    }
}
```

### Respecting cancellation when posting

```kotlin
val damage = DamageEvent("Player", 1000.0)

// Ignores cancel():
bus.post(damage)

// Honors cancel():
bus.post(damage, true)
```

When `respectCancels = true`, event handlers run in **priority order**, and if any handler cancels the event, **lower-priority handlers will not run**.

---

# Modifiable Events

Some events may allow mutation of fields to influence downstream logic:

```kotlin
import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.raywire.api.Modifiable

class ChatMessageEvent(var message: String)
    : Event, Modifiable by Modifiable()
```

Example use:

```kotlin
class ChatFilter {

    @EventHandler
    fun onChat(event: ChatMessageEvent) {
        if (!event.message.startsWith("[Server]")) {
            event.message = "[Server] ${event.message}"
            event.markModified()
        }
    }
}
```

---

# Dependencies

- [Fabric Language Kotlin](https://github.com/FabricMC/fabric-language-kotlin)

# Contributing

Contributions are welcome!  
Open issues or pull requests on the GitHub repo:  
https://github.com/Apollointhehouse/Raywire

# License

Raywire is licensed under the Apache 2.0 license.  
See the [LICENSE](LICENSE) file for details.

