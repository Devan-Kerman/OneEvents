package net.devtech.onemixin.access;

import net.minecraft.nbt.CompoundTag;

public interface ChunkDataAccess {
	int getData();
	void setData(int data);

	void init(CompoundTag tag);
}
