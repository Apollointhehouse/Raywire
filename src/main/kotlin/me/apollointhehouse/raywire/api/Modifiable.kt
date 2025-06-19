package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.api.impl.ModifiableImpl

fun Modifiable(): Modifiable = ModifiableImpl()

interface Modifiable {
	var modified: Boolean
}
