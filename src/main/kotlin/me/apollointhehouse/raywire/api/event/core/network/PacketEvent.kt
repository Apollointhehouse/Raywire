package me.apollointhehouse.raywire.api.event.core.network

import me.apollointhehouse.raywire.api.Cancellable
import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.raywire.api.Modifiable
import me.apollointhehouse.raywire.api.impl.CancellableImpl
import me.apollointhehouse.raywire.api.impl.ModifiableImpl
import net.minecraft.core.net.packet.Packet

sealed class PacketEvent(val packet: Packet) :
	Event,
	Modifiable by ModifiableImpl(),
	Cancellable by CancellableImpl() {

	class Send(packet: Packet) : PacketEvent(packet)
	class Receive(packet: Packet) : PacketEvent(packet)
}
