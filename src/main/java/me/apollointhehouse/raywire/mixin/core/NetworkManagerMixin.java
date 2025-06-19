package me.apollointhehouse.raywire.mixin.core;

import me.apollointhehouse.raywire.api.event.core.network.PacketEvent;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = NetworkManager.class, remap = false)
public class NetworkManagerMixin {
	@Redirect(method = "processReadPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/packet/Packet;handlePacket(Lnet/minecraft/core/net/handler/PacketHandler;)V"))
	void onHandlePacket(Packet packet, PacketHandler packetHandler) {
		PacketEvent.Receive event = new PacketEvent.Receive(packet);
		event.call();

		if (!event.isCancelled())
			packet.handlePacket(packetHandler);
	}
}
