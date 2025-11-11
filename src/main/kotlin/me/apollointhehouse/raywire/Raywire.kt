package me.apollointhehouse.raywire

import me.apollointhehouse.raywire.api.Bus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Raywire {
	internal const val MOD_ID: String = "raywire"
	internal val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	val globalBus = Bus()
}
