package net.byAqua3.thetitansneo.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemFoodMalgrum extends Item {

	public ItemFoodMalgrum(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
        return true;
    }

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		float maxHealth = entity.getMaxHealth();
		ExperienceOrb orb = new ExperienceOrb(level, entity.getX(), entity.getY() + 0.5D, entity.getZ(), 5 + entity.getRandom().nextInt(10) + (int) maxHealth);
		if (!level.isClientSide()) {
			level.addFreshEntity(orb);
		}
		entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, (entity.hasEffect(MobEffects.ABSORPTION) && entity.getEffect(MobEffects.ABSORPTION).getAmplifier() > 9) ? (entity.getEffect(MobEffects.ABSORPTION).getAmplifier() + 9) : 9));
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2400, 9));
		entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 18000, 0));
		entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() + 40.0D);
		if (entity.getMaxHealth() > 100.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 18000, 1));
		}
		if (entity.getMaxHealth() > 200.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 18000, 0));
		}
		if (entity.getMaxHealth() > 300.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 18000, 3));
		}
		if (entity.getMaxHealth() > 400.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 18000, 1));
		}
		if (entity.getMaxHealth() > 600.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 18000, 1));
		}
		if (entity.getMaxHealth() > 800.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 18000, 1));
		}
		if (entity.getMaxHealth() > 1000.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 18000, 1));
		}
		if (entity.getMaxHealth() > 1500.0F) {
			entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 18000, 3));
			entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 18000, 99));
			entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 18000, 99));
			entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() + 1.0D);
		}
		return super.finishUsingItem(stack, level, entity);
	}

}
