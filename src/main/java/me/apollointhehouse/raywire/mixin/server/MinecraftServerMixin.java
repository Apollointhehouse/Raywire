package me.apollointhehouse.raywire.mixin.server;

import me.apollointhehouse.raywire.api.*;
import me.apollointhehouse.raywire.api.event.core.TickEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MinecraftServerMixin {
	@Inject(method = "doTick", at = @At("HEAD"), cancellable = true)
	public void preTick(CallbackInfo info) {
		CancellableEvent event = new TickEvent.Pre();
		event.invoke();
		if (event.isCancelled()) info.cancel();
	}

	@Inject(method = "doTick", at = @At("RETURN"))
	public void postTick(CallbackInfo info) {
		Event event = new TickEvent.Post();
		event.invoke();
	}
}
