package me.apollointhehouse.raywire.api.event.core

import me.apollointhehouse.raywire.api.Cancellable
import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.raywire.api.impl.CancellableImpl

sealed interface TickEvent : Event {
	object Pre : TickEvent, Cancellable by CancellableImpl()
	object Post : TickEvent
}
