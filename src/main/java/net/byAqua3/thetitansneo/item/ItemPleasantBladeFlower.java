package net.byAqua3.thetitansneo.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemPleasantBladeFlower extends Item {

	public ItemPleasantBladeFlower(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		entity.removeEffect(MobEffects.BLINDNESS);
		entity.removeEffect(MobEffects.CONFUSION);
		entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1000, 1));
		return super.finishUsingItem(stack, level, entity);
	}

}
