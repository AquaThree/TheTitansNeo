package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;

public class ItemAdminiumPickaxe extends PickaxeItem {

	public ItemAdminiumPickaxe(Properties properties) {
		super(TheTitansNeoTiers.ADMINIUM, properties.attributes(ItemAdminiumSword.createAttributes(1000000000.0F, Float.MAX_VALUE)));
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity itemEntity) {
		itemEntity.setInvulnerable(true);
		return super.onEntityItemUpdate(stack, itemEntity);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
        return true;
    }

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		if (entity != null) {
			if (entity.getBbHeight() >= 6.0F || entity instanceof EntityTitan || !entity.onGround()) {
				entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				entity.hurt(entity.damageSources().mobAttack(attacker), 3.0E9F);
			}
		}
		return super.hurtEnemy(stack, entity, attacker);
	}
}
