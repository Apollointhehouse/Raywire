package me.apollointhehouse.raywire.mixin.core;

import me.apollointhehouse.raywire.api.event.core.network.PacketEvent;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.DataOutputStream;

@Mixin(value = Packet.class, remap = false)
public class PacketMixin {
	@Inject(at = @At("HEAD"), method = "writePacket", remap = false, cancellable = true)
	private static void onWritePacket(Packet packet, DataOutputStream dos, CallbackInfo info) {
		PacketEvent.Send event = new PacketEvent.Send(packet);
		event.call();

		if (event.isCancelled()) info.cancel();
	}
}
