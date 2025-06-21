package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.Raywire.globalRegistry

interface Event {
	fun call() = globalRegistry.invoke(this)
}
