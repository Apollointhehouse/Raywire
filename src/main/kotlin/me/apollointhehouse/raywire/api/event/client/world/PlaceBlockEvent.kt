package me.apollointhehouse.raywire.api.event.client.world

import me.apollointhehouse.raywire.api.ModifiableEvent
import net.minecraft.core.entity.player.Player
import net.minecraft.core.item.ItemStack
import net.minecraft.core.util.helper.Side
import net.minecraft.core.world.World

sealed class PlaceBlockEvent(
    val player: Player,
    val world: World,
    val itemstack: ItemStack?,
    val blockX: Int,
    val blockY: Int,
    val blockZ: Int,
    val side: Side,
    val xPlaced: Double,
    val yPlaced: Double,
) : ModifiableEvent() {
    class Pre(
        player: Player,
        world: World,
        itemstack: ItemStack?,
        blockX: Int,
        blockY: Int,
        blockZ: Int,
        side: Side,
        xPlaced: Double,
        yPlaced: Double
    ) : PlaceBlockEvent(player, world, itemstack, blockX, blockY, blockZ, side, xPlaced, yPlaced)

    class Post(
        player: Player,
        world: World,
        itemstack: ItemStack?,
        blockX: Int,
        blockY: Int,
        blockZ: Int,
        side: Side,
        xPlaced: Double,
        yPlaced: Double
    ) : PlaceBlockEvent(player, world, itemstack, blockX, blockY, blockZ, side, xPlaced, yPlaced)
}
