package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ItemVoidHoe extends HoeItem {

	public ItemVoidHoe(Properties properties) {
		super(TheTitansNeoTiers.VOID, properties.attributes(HoeItem.createAttributes(TheTitansNeoTiers.VOID, 1500, 0.0F)));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		if (entity != null) {
			if (entity.getBbHeight() >= 6.0F || entity instanceof EntityTitan || !entity.onGround()) {
				entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				entity.hurt(entity.damageSources().mobAttack(attacker), 5000.0F);
			} else if (!(entity instanceof Player) && !(entity instanceof AbstractGolem)) {
				entity.remove(Entity.RemovalReason.KILLED);
				entity.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
				attacker.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1, 3, false, false));
				attacker.getAttribute(Attributes.MAX_HEALTH).setBaseValue(attacker.getMaxHealth() + entity.getMaxHealth());
				attacker.heal(entity.getMaxHealth());
				if (entity instanceof Mob && entity.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)) {
					attacker.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attacker.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() + entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
				}
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
