package me.apollointhehouse.raywire.mixin.server;

import me.apollointhehouse.raywire.api.event.core.TickEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MinecraftServerMixin {
	@Inject(method = "doTick", at = @At("HEAD"), cancellable = true)
	public void preTick(CallbackInfo info) {
		TickEvent.Pre event = TickEvent.Pre.INSTANCE;
		event.call();
		if (event.isCancelled()) info.cancel();
	}

	@Inject(method = "doTick", at = @At("RETURN"))
	public void postTick(CallbackInfo info) {
		TickEvent.Post event = TickEvent.Post.INSTANCE;
		event.call();
	}
}
