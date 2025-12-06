# Raywire

Raywire is an event library for Better Than Adventure (BTA), designed to provide a flexible and efficient event-driven system for BTA mod developers. 

Written in Kotlin, Raywire makes it easy to create, subscribe to, and manage custom events within your BTA mods.

## Features

- Simple and extensible event bus system
- Annotation-based event listeners
- Priority-based event handling
- Kotlin-first API, fully interoperable with Java
- Lightweight and fast

## Prerequisites

- JDK 21 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community Edition is sufficient)
- Minecraft Development plugin (optional, but recommended)
- BTA modding environment set up

## Using Raywire in Your Mod
Add Raywire as a dependency in your mod's build.gradle.kts:
```kotlin
dependencies {
    modImplementation("com.github.Apollointhehouse:Raywire:<version>")
}
```

Replace \<version\> with the latest Raywire release.

## Example Usage
Create an event listener in Kotlin:
```kotlin
class Foo {
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

Create an event:
```kotlin
class FooEvent(val bar: String) : Event
```

Create an event bus:
```kotlin
val bus = Bus()
```

Or use the global bus:
```kotlin
val bus = Raywire.globalBus
```

Then subscribe the listener:
```kotlin
bus.subscribe(Foo())
```

Post an event:
```kotlin
bus.post(FooEvent(bar))
```

## Dependencies

- [Fabric Language Kotlin 1.11.0](https://github.com/FabricMC/fabric-language-kotlin)

## Contributing
Contributions are welcome! Please open issues or pull requests on the [GitHub repository](https://github.com/Apollointhehouse/Raywire).


## License
Raywire is licensed under the MIT License. See the LICENSE file for details.
