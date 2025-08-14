package net.byAqua3.thetitansneo.item;

import java.util.List;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemAdminiumSword extends SwordItem {

	public ItemAdminiumSword(Properties properties) {
		super(TheTitansNeoTiers.ADMINIUM, properties.attributes(ItemAdminiumSword.createAttributes(1000000000.0F, Float.MAX_VALUE)));
	}

	public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
		return ItemAttributeModifiers.builder().add(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.withDefaultNamespace("attack_speed"), attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.withDefaultNamespace("attack_damage"), attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
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
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		if (entity != null) {
			entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
			if (entity instanceof EntityTitan) {
				EntityTitan titan = (EntityTitan) entity;
				if (titan.canBeHurtByPlayer() && titan.getInvulTime() <= 0) {
					titan.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
					titan.setTitanHealth(Math.max(titan.getTitanHealth() - 1000.0F, 0.0F));
					titan.setTarget(attacker);
				}
			} else {
				entity.invulnerableTime = 0;
				entity.hurt(entity.damageSources().mobAttack(attacker), Float.MAX_VALUE);
				entity.setHealth(0.0F);
				entity.push(0.0D, 1.0D, 0.0D);
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
		Tool tool = stack.get(DataComponents.TOOL);

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
		stack.hurtAndBreak(tool.damagePerBlock(), player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
		player.swing(player.getUsedItemHand());

		Vec3 vec3 = player.getViewVector(1.0F);
		double dx = vec3.x * 4.0D;
		double dy = player.getEyeHeight() + vec3.y * 4.0D;
		double dz = vec3.z * 4.0D;
		List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(4.0D, 4.0D, 4.0D).move(dx, dy, dz));
		for (Entity entity : entities) {
			if (entity != null) {
				entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
				
				if (entity instanceof EntityTitan) {
					EntityTitan titan = (EntityTitan) entity;
					if (titan.canBeHurtByPlayer() && titan.getInvulTime() <= 0) {
						entity.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);
						
						titan.animateHurt(180.0F);
						titan.setTitanHealth(Math.max(titan.getTitanHealth() - 2000.0F * (f + 1.0F), 0.0F));
						titan.setTarget(player);
					}
				} else if (entity instanceof LivingEntity) {
					LivingEntity livingEntity1 = (LivingEntity) entity;
					livingEntity1.animateHurt(180.0F);
					livingEntity1.setHealth(Math.max(livingEntity1.getHealth() - 2000.0F * (f + 1.0F), 0.0F));
					if (livingEntity1.getHealth() <= 0.0F) {
						livingEntity1.die(entity.damageSources().playerAttack(player));
					}
					livingEntity1.push(0.0D, 1.0D, 0.0D);
				}
			}
		}
	}

}
