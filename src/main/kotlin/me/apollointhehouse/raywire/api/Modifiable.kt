package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.internal.ModifiableImpl

interface Modifiable {
    companion object {
        operator fun invoke(): Modifiable = ModifiableImpl()
    }

    var modified: Boolean
}
