package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.Raywire.registry

interface Event {
	operator fun invoke() = registry.invoke(this)
}
