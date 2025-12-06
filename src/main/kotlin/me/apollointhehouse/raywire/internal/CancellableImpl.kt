package me.apollointhehouse.raywire.internal

import me.apollointhehouse.raywire.api.Cancellable

internal class CancellableImpl : Cancellable {
    private var cancelled = false

    override fun isCancelled(): Boolean = cancelled

    override fun cancel() {
        cancelled = true
    }
}
