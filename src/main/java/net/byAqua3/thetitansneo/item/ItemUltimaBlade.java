package net.byAqua3.thetitansneo.item;

import java.util.ArrayList;
import java.util.List;

import net.byAqua3.thetitansneo.damage.DamageSourceTitanAttack;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ItemUltimaBlade extends SwordItem {

	public ItemUltimaBlade(Properties properties) {
		super(TheTitansNeoTiers.ULTIMA, properties.attributes(createAttributes(TheTitansNeoTiers.ULTIMA, 0.0F, Float.MAX_VALUE, 24.0F)));
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed, float range) {
		return ItemAttributeModifiers.builder().add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_block_interaction_range"), range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double) attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double) ((float) attackDamage + tier.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity itemEntity) {
		itemEntity.setInvulnerable(true);
		return super.onEntityItemUpdate(stack, itemEntity);
	}

	@Override
	public int getDamage(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.translatable("item.thetitansneo.ultima_blade.info1").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.DARK_AQUA));
		tooltip.add(Component.translatable("item.thetitansneo.ultima_blade.info2").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.DARK_AQUA));
		tooltip.add(Component.translatable("item.thetitansneo.ultima_blade.info3").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.DARK_AQUA));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (entity instanceof Player) {
			Player player = (Player) entity;

			if (TheTitansNeoConfigs.ultimaBladeShowParticles.get()) {
				for (int i = 0; i < 3; i++) {
					level.addParticle(ParticleTypes.FIREWORK, player.getX() + (level.random.nextDouble() - 0.5D) * player.getBbWidth(), player.getY() + level.random.nextDouble() * player.getBbHeight(), player.getZ() + (level.random.nextDouble() - 0.5D) * player.getBbWidth(), (level.random.nextDouble() - 0.5D) * 2.0D, 1.0D, (level.random.nextDouble() - 0.5D) * 2.0D);
					level.addParticle(ParticleTypes.POOF, player.getX() + (level.random.nextDouble() - 0.5D) * player.getBbWidth(), player.getY() + level.random.nextDouble() * player.getBbHeight(), player.getZ() + (level.random.nextDouble() - 0.5D) * player.getBbWidth(), (level.random.nextDouble() - 0.5D) * 2.0D, 1.0D, (level.random.nextDouble() - 0.5D) * 2.0D);
				}
			}

			if (player.getY() < -100.0D) {
				player.push(0.0D, 10.0D, 0.0D);
			}

			player.heal(player.getMaxHealth());
			if (!level.isClientSide()) {
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 5, 249, false, false));
			}
		}
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (livingEntity instanceof Player) {
			Player player = (Player) livingEntity;
			Block block = state.getBlock();

			player.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.0F);

			if (!state.isAir()) {
				if (!level.isClientSide() && !player.isCreative()) {
					List<ItemStack> drops = new ArrayList<>();
					List<ItemStack> blockDrops = Block.getDrops(state, (ServerLevel) level, pos, null);

					if (!blockDrops.isEmpty()) {
						drops.addAll(blockDrops);
					} else {
						drops.add(new ItemStack(block));
					}

					if (!drops.isEmpty()) {
						for (ItemStack drop : drops) {
							ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), drop);
							itemEntity.setDefaultPickUpDelay();
							itemEntity.push(-Math.sin(player.getYRot() * Math.PI / 180.0F) * 1.0D, 0.75D, Math.cos(player.getYRot() * Math.PI / 180.0F) * 1.0D);
							level.addFreshEntity(itemEntity);
						}
					}
				}

				if (!(block instanceof BaseFireBlock)) {
					level.levelEvent(2001, pos, Block.getId(state));
				}
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));
			}
		}
		return super.mineBlock(stack, level, state, pos, livingEntity);
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity livingEntity) {
		Level level = livingEntity.level();

		if (livingEntity instanceof Player) {
			Player player = (Player) livingEntity;

			if (player.isCrouching()) {
				List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(64.0D, 64.0D, 64.0D));
				for (Entity entity : entities) {
					if (entity != null) {
						DamageSourceTitanAttack damageSource = new DamageSourceTitanAttack(player);

						player.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 2.0F, 0.5F + level.random.nextFloat() * 0.25F);
						player.playSound(TheTitansNeoSounds.SLASH_FLESH.get(), 10.0F, 1.0F);

						if (entity instanceof EntityTitan) {
							player.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);

							EntityTitan titan = (EntityTitan) entity;
							titan.hurt(damageSource, 2000.0F);
						} else if (entity instanceof LivingEntity) {
							entity.setRemainingFireTicks(Integer.MAX_VALUE);
							entity.hurt(damageSource, Float.MAX_VALUE);
							entity.push(-Math.sin(player.getYRot() * Math.PI / 180.0F) * 6.0F, 6.0D, Math.cos(player.getYRot() * Math.PI / 180.0F) * 6.0F);
						} else {
							entity.hurt(entity.damageSources().playerAttack(player), 20000.0F);
						}
					}
				}
			}

			player.playSound(TheTitansNeoSounds.TITAN_SWING.get(), 10.0F, 1.0F);

			for (int i = 0; i < 24; i++) {
				Vec3 vec3 = player.getViewVector(1.0F);
				double dx = vec3.x * i;
				double dy = player.getEyeHeight() + vec3.y * i;
				double dz = vec3.z * i;

				List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(3.0D, 3.0D, 3.0D).move(dx, dy, dz));
				for (Entity entity : entities) {
					if (entity != null) {
						DamageSourceTitanAttack damageSource = new DamageSourceTitanAttack(player);

						player.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 2.0F, 0.5F + level.random.nextFloat() * 0.25F);
						player.playSound(TheTitansNeoSounds.SLASH_FLESH.get(), 10.0F, 1.0F);

						if (entity instanceof EntityTitan) {
							player.playSound(TheTitansNeoSounds.TITAN_PUNCH.get(), 10.0F, 1.0F);

							EntityTitan titan = (EntityTitan) entity;
							titan.hurt(damageSource, 2000.0F);
						} else if (entity instanceof PrimedTnt) {
							if (!level.isClientSide()) {
								level.explode(player, entity.getX(), entity.getY(), entity.getZ(), 4.0F, false, Level.ExplosionInteraction.BLOCK);
								entity.remove(RemovalReason.KILLED);
							}
						} else if (entity instanceof Fireball) {
							if (!level.isClientSide()) {
								level.explode(player, entity.getX(), entity.getY(), entity.getZ(), 0.0F, false, Level.ExplosionInteraction.BLOCK);
								entity.remove(RemovalReason.KILLED);
							}
						} else if (entity instanceof LivingEntity) {
							entity.setRemainingFireTicks(Integer.MAX_VALUE);
							entity.hurt(damageSource, 20000.0F);
							entity.push(-Math.sin(player.getYRot() * Math.PI / 180.0F) * 6.0F, 6.0D, Math.cos(player.getYRot() * Math.PI / 180.0F) * 6.0F);
						}
					}
				}
			}
		}
		return false;
	}
}
