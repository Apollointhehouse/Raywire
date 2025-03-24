package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.Raywire.registry

interface Event {
	fun call() = registry.invoke(this)
}
