package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.byAqua3.thetitansneo.gen.IChunkGeneratorGenStructures;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkGenerator;

@Mixin({ ChunkGenerator.class })
public class MixinChunkGenerator {

	@WrapOperation(method = { "applyBiomeDecoration" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/level/StructureManager;shouldGenerateStructures()Z") })
	public boolean shouldGenerateStructures(StructureManager structureManager, Operation<Boolean> original) {
		ChunkGenerator chunkGenerator = (ChunkGenerator) (Object) this;
		if (chunkGenerator instanceof IChunkGeneratorGenStructures) {
			IChunkGeneratorGenStructures genStructures = (IChunkGeneratorGenStructures) chunkGenerator;
			switch (genStructures.shouldGenerateStructures()) {
			case DEFAULT:
				return original.call(new Object[] { structureManager });
			case TRUE:
				return true;
			case FALSE:
				return false;
			}
		}
		return original.call(new Object[] { structureManager });
	}
}
