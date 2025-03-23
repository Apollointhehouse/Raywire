package me.apollointhehouse.raywire

import me.apollointhehouse.raywire.api.Registry
import me.apollointhehouse.raywire.internal.EventManager
import net.fabricmc.api.ModInitializer
import org.slf4j.*

object Raywire : ModInitializer {
	internal const val MOD_ID: String = "raywire"
	@JvmField internal val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
	}

	val registry: Registry = EventManager()
}
