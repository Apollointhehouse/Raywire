package me.apollointhehouse.raywire.api.event.core.network

import me.apollointhehouse.raywire.api.Cancellable
import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.raywire.api.Modifiable
import net.minecraft.core.net.packet.Packet

sealed class PacketEvent(val packet: Packet) :
	Event,
	Modifiable by Modifiable(),
	Cancellable by Cancellable() {

	class Send(packet: Packet) : PacketEvent(packet)
	class Receive(packet: Packet) : PacketEvent(packet)
}
