package me.apollointhehouse.raywire.api.event.client.world

import me.apollointhehouse.raywire.api.ModifiableEvent
import net.minecraft.core.sound.SoundTypes

class PlaySoundEvent(
    val soundPath: String,
    val soundType: SoundTypes,
    val x: Float,
    val y: Float,
    val z: Float,
    val volume: Float,
    val pitch: Float
) : ModifiableEvent()
