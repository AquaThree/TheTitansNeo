package net.byAqua3.thetitansneo.item;

import java.util.List;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemHarcadiumSword extends SwordItem {

	public ItemHarcadiumSword(Properties properties) {
		super(TheTitansNeoTiers.HARCADIUM, properties.attributes(SwordItem.createAttributes(TheTitansNeoTiers.HARCADIUM, 50, -2.4F)));
	}
	
	@Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
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
				entity.hurt(entity.damageSources().mobAttack(attacker), 500.0F);
			}
		}
		return super.hurtEnemy(stack, entity, attacker);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		InteractionResultHolder<ItemStack> ret = net.neoforged.neoforge.event.EventHooks.onArrowNock(stack, level, player, hand, true);
		if (ret != null) {
			return ret;
		}
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(stack);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int time) {
		Player player = (Player) livingEntity;

		int max = this.getUseDuration(stack, livingEntity);
		int j = max - time;

		float f = j / 60.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f < 0.1) {
			return;
		}

		if (f > 1.0) {
			f = 1.0F;
		}
		player.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 1.0F, 2.0F);
		player.swing(player.getUsedItemHand());
		Vec3 vec3 = player.getViewVector(1.0F);
		double dx = vec3.x * 4.0D;
		double dy = player.getEyeHeight() + vec3.y * 4.0D;
		double dz = vec3.z * 4.0D;
		List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(4.0D, 4.0D, 4.0D).move(dx, dy, dz));
		for (Entity entity : entities) {
			if (entity != null && entity instanceof LivingEntity) {
				entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				entity.playSound(TheTitansNeoSounds.SLASH_FLESH.get(), 2.0F, 1.25F);
				entity.hurt(entity.damageSources().mobAttack(player), 1000.0F * (f + 1.0F));
			}
		}
	}

}
