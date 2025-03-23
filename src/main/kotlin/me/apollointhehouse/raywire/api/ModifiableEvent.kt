package me.apollointhehouse.raywire.api

abstract class ModifiableEvent : CancellableEvent() {
	var modified = false
}
