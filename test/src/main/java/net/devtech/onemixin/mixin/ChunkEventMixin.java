package net.devtech.onemixin.mixin;

import net.devtech.onemixin.access.ChunkDataAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.poi.PointOfInterestStorage;

@Mixin(ChunkSerializer.class)
public class ChunkEventMixin {
	// change Integer to the object of your choice
	@Mixin(WorldChunk.class)
	public static class WorldChunkMixin implements ChunkDataAccess {
		@Unique
		private int data;

		@Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/ProtoChunk;)V", at = @At("TAIL"))
		private void transfer(World world, ProtoChunk protoChunk, CallbackInfo ci) {
			this.data = ((ChunkDataAccess)protoChunk).getData();
		}

		@Override
		public int getData() {
			return this.data;
		}

		@Override
		public void setData(int data) {
			this.data = data;
		}

		@Override
		public void init(CompoundTag tag) {
			this.data = deserialize(tag);
		}
	}

	@Mixin(ProtoChunk.class)
	public static class ProtoChunkMixin implements ChunkDataAccess {
		@Unique
		private int data;

		@Override
		public int getData() {
			return this.data;
		}

		@Override
		public void setData(int data) {
			this.data = data;
		}

		@Override
		public void init(CompoundTag tag) {
			this.data = deserialize(tag);
		}
	}

	@Inject (method = "deserialize", at = @At(value = "RETURN"))
	private static void newWorld(ServerWorld serverWorld, StructureManager structureManager, PointOfInterestStorage pointOfInterestStorage, ChunkPos chunkPos, CompoundTag compoundTag, CallbackInfoReturnable<ProtoChunk> cir) {
		ProtoChunk acc = cir.getReturnValue();
		ChunkDataAccess access;
		if(acc instanceof ReadOnlyChunk) {
			access = (ChunkDataAccess) ((ReadOnlyChunk) acc).getWrappedChunk();
		} else {
			access = (ChunkDataAccess) acc; // proto chunk
		}

		access.setData(deserialize(compoundTag));
	}
	
	@Inject(method = "serialize", at = @At("RETURN"))
	private static void serialize(ServerWorld serverWorld, Chunk chunk, CallbackInfoReturnable<CompoundTag> cir) {
		serialize(((ChunkDataAccess)chunk).getData(), cir.getReturnValue());
	}

	@Unique
	private static int deserialize(CompoundTag tag) {
		return tag.getInt("i_enum_20");
	}
	
	@Unique
	private static void serialize(int obj, CompoundTag tag) {
		tag.putInt("i_enum_20", obj);
	}
}
