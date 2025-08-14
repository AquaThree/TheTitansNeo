package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ItemMalgrumSeeds extends ItemNameBlockItem {

	public ItemMalgrumSeeds(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult interactionResult = super.useOn(context);
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		if (interactionResult.consumesAction()) {
			level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), TheTitansNeoSounds.TITAN_PRESS.get(), SoundSource.BLOCKS, 0.5F, 1.0F, false);
		}
		return InteractionResult.FAIL;
	}

}
