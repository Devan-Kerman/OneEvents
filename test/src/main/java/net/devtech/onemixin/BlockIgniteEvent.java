package net.devtech.onemixin;

import net.devtech.onemixin.mixin.fire.ExplosionIgnite;
import net.devtech.onemixin.mixin.fire.LavaIgnite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Q: Why not inject into the head of World#setBlockState
 * A: because that will only give you the location of the new fire block, and not the block being ignited
 * @see LavaIgnite
 * @see ExplosionIgnite
 */
public class BlockIgniteEvent {
	/**
	 * return true if the block cannot be ignited by fire (assuming the block is flammable)
	 * this does not guarantee the block will be lit by neighboring blocks
	 *
	 * @param world the world the block is trying to be lit
	 * @param pos the position of the block trying to be lit
	 */
	public static boolean cannotIgnite(World world, BlockPos pos) {
		return false;
	}
}
