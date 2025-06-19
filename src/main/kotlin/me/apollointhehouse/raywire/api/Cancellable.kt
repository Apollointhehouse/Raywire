package me.apollointhehouse.raywire.api

interface Cancellable {
	fun isCancelled(): Boolean
	fun cancel()
}
