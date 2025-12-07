# Raywire

Raywire is an event library for Better Than Adventure (BTA), designed to provide a flexible and efficient event-driven system for BTA mod developers.

Written in Kotlin, Raywire makes it easy to create, subscribe to, and manage custom events within your BTA mods.

## Prerequisites

- JDK 21 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community Edition is sufficient)
- Minecraft Development plugin (optional, but recommended)
- BTA modding environment set up with the mod targeting Java 17

## Using Raywire in Your Mod

Add Raywire as a dependency in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Apollointhehouse:Raywire:<version>")
}
```

Replace `<version>` with the latest Raywire release.

---

# Usage

Take a look at the [Event Library](https://github.com/SmushyTaco/Event-Library) for information on how to use this.

This mod provides a global bus you can optionally choose to use:

```kotlin
import me.apollointhehouse.raywire.Raywire

val bus = Raywire.GLOBAL_BUS
```

# Dependencies

- [Fabric Language Kotlin](https://github.com/FabricMC/fabric-language-kotlin)

# Contributing

Contributions are welcome!  
Open issues or pull requests on the GitHub repo:  
https://github.com/Apollointhehouse/Raywire

# License

Raywire is licensed under the Apache 2.0 license.  
See the [LICENSE](LICENSE) file for details.

