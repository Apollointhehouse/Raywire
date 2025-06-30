package me.apollointhehouse.raywire

import me.apollointhehouse.raywire.api.Bus
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Raywire : ModInitializer {
	internal const val MOD_ID: String = "raywire"
	@JvmField internal val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {}

	val globalBus = Bus()
}
