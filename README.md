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

- JDK 17 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community Edition is sufficient)
- Gradle (handled automatically by IntelliJ)
- Minecraft Development plugin (optional, but recommended)
- BTA modding environment set up

## Getting Started

### 1. Cloning the Repository
Clone Raywire to your local machine:
```sh
git clone https://github.com/yourusername/raywire.git
```

### 2. Importing the Project
Open IntelliJ IDEA and select Open to import the project. IntelliJ will automatically detect the Gradle build and set up the environment.

### 3. Building the Mod
To build Raywire, use the Gradle build task:
```sh
./gradlew build
```
The compiled JAR will be located in build/libs/.

### 4. Using Raywire in Your Mod
Add Raywire as a dependency in your mod's build.gradle.kts:
```kotlin
dependencies {
    modImplementation("me.apollointhehouse:Raywire:<version>")
}
```

Replace <version> with the latest Raywire release.

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

Create an event registry:
```kotlin
val registry = Registry()
```

Or use the global registry:
```kotlin
val registry = Raywire.globalRegistry
```

Then subscribe the listener:
```kotlin
registry.subscribe(Foo())
```

Post an event:
```kotlin
registry.invoke(FooEvent(bar))
```

## Contributing
Contributions are welcome! Please open issues or pull requests on the [GitHub repository](https://github.com/Apollointhehouse/Raywire).


## License
Raywire is licensed under the MIT License. See the LICENSE file for details.
