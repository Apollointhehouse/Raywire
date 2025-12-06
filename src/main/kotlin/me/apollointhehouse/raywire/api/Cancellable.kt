package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.internal.CancellableImpl

interface Cancellable {
    companion object {
        operator fun invoke(): Cancellable = CancellableImpl()
    }

    fun isCancelled(): Boolean

    fun cancel()
}
