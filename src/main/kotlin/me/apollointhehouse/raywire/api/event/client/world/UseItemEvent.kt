package me.apollointhehouse.raywire.api.event.client.world

import me.apollointhehouse.raywire.api.ModifiableEvent
import net.minecraft.core.entity.player.Player
import net.minecraft.core.item.ItemStack
import net.minecraft.core.world.World

sealed class UseItemEvent(
    val player: Player,
    val world: World,
    val stack: ItemStack
) : ModifiableEvent() {
    class Pre(
        player: Player,
        world: World,
        stack: ItemStack
    ) : UseItemEvent(player, world, stack)
    class Post(
        player: Player,
        world: World,
        stack: ItemStack
    ) : UseItemEvent(player, world, stack)
}
