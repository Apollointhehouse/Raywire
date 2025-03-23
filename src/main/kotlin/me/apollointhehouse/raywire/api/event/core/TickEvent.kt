package me.apollointhehouse.raywire.api.event.core

import me.apollointhehouse.raywire.api.CancellableEvent
import me.apollointhehouse.raywire.api.Event

sealed interface TickEvent : Event {
    class Pre : TickEvent, CancellableEvent()
    class Post : TickEvent
}
