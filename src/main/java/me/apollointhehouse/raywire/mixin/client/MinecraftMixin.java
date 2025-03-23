package me.apollointhehouse.raywire.mixin.client;

import me.apollointhehouse.raywire.api.*;
import me.apollointhehouse.raywire.api.event.core.TickEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Inject(method = "runTick", at = @At("HEAD"), cancellable = true)
	public void preTick(CallbackInfo info) {
		CancellableEvent event = new TickEvent.Pre();
		event.invoke();
		if (event.isCancelled()) info.cancel();
	}

	@Inject(method = "runTick", at = @At("RETURN"))
	public void postTick(CallbackInfo info) {
		Event event = new TickEvent.Post();
		event.invoke();
	}
}
