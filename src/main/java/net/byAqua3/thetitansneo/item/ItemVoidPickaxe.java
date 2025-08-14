package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.UseAnim;

public class ItemVoidPickaxe extends PickaxeItem {

	public ItemVoidPickaxe(Properties properties) {
		super(TheTitansNeoTiers.VOID, properties.attributes(PickaxeItem.createAttributes(TheTitansNeoTiers.VOID, 1498, -2.8F)));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.BOW;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		if (entity != null) {
			if (entity.getBbHeight() >= 6.0F || entity instanceof EntityTitan || !entity.onGround()) {
				entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				entity.hurt(entity.damageSources().mobAttack(attacker), 1000.0F);
			}
		}
		return super.hurtEnemy(stack, entity, attacker);
	}

}
