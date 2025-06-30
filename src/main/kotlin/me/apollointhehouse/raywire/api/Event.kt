package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.Raywire.globalBus

interface Event {
	fun call() = globalBus.post(this)
}
