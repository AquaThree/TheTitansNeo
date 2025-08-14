package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ItemHarcadiumHoe extends HoeItem {

	public ItemHarcadiumHoe(Properties properties) {
		super(TheTitansNeoTiers.HARCADIUM, properties.attributes(HoeItem.createAttributes(TheTitansNeoTiers.HARCADIUM, 46, 0.0F)));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		if (entity != null) {
			if (entity.getBbHeight() >= 6.0F || entity instanceof EntityTitan || !entity.onGround()) {
				entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				entity.hurt(entity.damageSources().mobAttack(attacker), 300.0F);
			}
		}
		return super.hurtEnemy(stack, entity, attacker);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		BlockPos blockPos = context.getClickedPos();
		BlockState blockState = level.getBlockState(blockPos);
		Block block = blockState.getBlock();
		if (level.getBlockState(blockPos.atY(blockPos.getY() + 1)).isAir() && (block == Blocks.SPONGE || block == Blocks.WET_SPONGE || block == Blocks.GRAVEL)) {
			level.playSound(player, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.setBlockAndUpdate(blockPos, Blocks.FARMLAND.defaultBlockState());
			if (!level.isClientSide()) {
				if (player != null) {
					context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
				}
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return super.useOn(context);
	}

}
