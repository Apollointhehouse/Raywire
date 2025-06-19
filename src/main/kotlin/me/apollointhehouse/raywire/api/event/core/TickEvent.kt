package me.apollointhehouse.raywire.api.event.core

import me.apollointhehouse.raywire.api.Cancellable
import me.apollointhehouse.raywire.api.Event

sealed interface TickEvent : Event {
	object Pre : TickEvent, Cancellable by Cancellable()
	object Post : TickEvent
}
