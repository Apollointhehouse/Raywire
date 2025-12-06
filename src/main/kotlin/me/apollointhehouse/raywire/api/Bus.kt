package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.internal.EventManager

interface Bus {
    companion object {
        operator fun invoke(): Bus = EventManager()
    }
    /**
     * Subscribes given object to scan for event handlers to be invoked
     * @param obj the object to be subscribed
     */
    fun subscribe(obj: Any)

    /**
     * Unsubscribes given object from being scanned for event handlers to be invoked
     * @param obj the object to be unsubscribed
     */
    fun unsubscribe(obj: Any)

    /**
     * Invokes all event handlers for given event
     * @param event Event being posted
     */
    fun post(event: Event)
}
