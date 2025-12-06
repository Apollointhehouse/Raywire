package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.internal.ModifiableImpl

fun Modifiable(): Modifiable = ModifiableImpl()

interface Modifiable {
	var modified: Boolean
}
