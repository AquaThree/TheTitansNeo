package net.byAqua3.thetitansneo.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

public class SavedDataSpawnedPos extends SavedData {

	private List<BlockPos> spawnedPos = new CopyOnWriteArrayList<>();
	
	public List<BlockPos> getSpawnedPos() {
		return spawnedPos;
	}

	public static SavedDataSpawnedPos load(CompoundTag tag, HolderLookup.Provider provider) {
		SavedDataSpawnedPos savedData = new SavedDataSpawnedPos();

		if (tag.contains("TitanSpawnedPos", Tag.TAG_LIST)) {
			ListTag tagList = tag.getList("TitanSpawnedPos", Tag.TAG_COMPOUND);

			for (int i = 0; i < tagList.size(); ++i) {
				CompoundTag compoundTag = tagList.getCompound(i);
				int x = compoundTag.getInt("x");
				int y = compoundTag.getInt("y");
				int z = compoundTag.getInt("z");

				savedData.getSpawnedPos().add(new BlockPos(x, y, z));
			}
		}
		return savedData;
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
		ListTag tagList = new ListTag();

		for (int i = 0; i < this.getSpawnedPos().size(); i++) {
			BlockPos blockPos = this.getSpawnedPos().get(i);
			CompoundTag compoundTag = new CompoundTag();
			compoundTag.putInt("x", blockPos.getX());
			compoundTag.putInt("y", blockPos.getY());
			compoundTag.putInt("z", blockPos.getZ());
			tagList.add(compoundTag);
		}

		if (!tagList.isEmpty()) {
			tag.put("TitanSpawnedPos", tagList);
		}
		return tag;
	}
}
