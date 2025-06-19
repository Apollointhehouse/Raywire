package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.api.impl.CancellableImpl

fun Cancellable(): Cancellable = CancellableImpl()

interface Cancellable {
	fun isCancelled(): Boolean
	fun cancel()
}
