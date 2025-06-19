package me.apollointhehouse.raywire.api.impl

import me.apollointhehouse.raywire.api.Cancellable

class CancellableImpl : Cancellable {
	private var cancelled = false
	override fun isCancelled(): Boolean = cancelled
	override fun cancel() { cancelled = true }
}
