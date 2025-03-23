package me.apollointhehouse.raywire.api.event.core.network

import me.apollointhehouse.raywire.api.ModifiableEvent
import net.minecraft.core.net.packet.Packet

sealed class PacketEvent(val packet: Packet) : ModifiableEvent() {
    class Send(packet: Packet) : PacketEvent(packet)
    class Receive(packet: Packet) : PacketEvent(packet)
}
