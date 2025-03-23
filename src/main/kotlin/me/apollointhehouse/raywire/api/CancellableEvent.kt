package me.apollointhehouse.raywire.api

abstract class CancellableEvent : Event {
	private var cancelled = false
	fun isCancelled(): Boolean = cancelled
	fun cancel() { cancelled = true }
}
