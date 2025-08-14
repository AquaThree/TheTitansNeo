package net.byAqua3.thetitansneo.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemPleasantBladeBrew extends Item {

	public ItemPleasantBladeBrew(Properties properties) {
		super(properties);
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		entity.removeEffect(MobEffects.POISON);
		entity.removeEffect(MobEffects.HUNGER);
		entity.removeEffect(MobEffects.CONFUSION);
		entity.removeEffect(MobEffects.WITHER);
		return super.finishUsingItem(stack, level, entity);
	}

}
