package net.byAqua3.thetitansneo.item;

import java.util.ArrayList;
import java.util.List;

import net.byAqua3.thetitansneo.entity.projectile.EntityHarcadiumArrow;
import net.byAqua3.thetitansneo.entity.titan.EntityItemTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoAttributes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDamageTypes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMobEffects;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class ItemAdminiumArmor extends ArmorItem {

	public ItemAdminiumArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties.attributes(createAttributes(type)));
	}

	public static ItemAttributeModifiers createAttributes(Type type) {
		ItemAttributeModifiers.Builder itemAttributeModifiers$builder = ItemAttributeModifiers.builder();
		if (type == Type.HELMET) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 14.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_KNOCKBACK_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 15.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 1250.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 100000.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.CHESTPLATE) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 26.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_KNOCKBACK_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 40.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 2000.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 100000.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.LEGGINGS) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 20.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_KNOCKBACK_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 30.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 1750.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 100000.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.BOOTS) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 10.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_KNOCKBACK_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 15.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 1000.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 100000.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		}
		return itemAttributeModifiers$builder.build();
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

	@SuppressWarnings("deprecation")
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);

		if (entity instanceof Player) {
			Player player = (Player) entity;

			stack.setDamageValue(stack.getDamageValue() - 10);

			if (this.getEquipmentSlot() == EquipmentSlot.HEAD && slotId == 39) {
				player.playSound(TheTitansNeoSounds.HARCACADIUM_HUM.get(), 5.0F, 0.5F);
				player.removeEffect(MobEffects.BLINDNESS);
				player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0));
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0));
			} else if (this.getEquipmentSlot() == EquipmentSlot.CHEST && slotId == 38) {
				player.playSound(TheTitansNeoSounds.HARCACADIUM_HUM.get(), 5.0F, 0.5F);
				player.removeEffect(MobEffects.WEAKNESS);
				player.removeEffect(MobEffects.DIG_SLOWDOWN);
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 99));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 3));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 999));
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0));
			} else if (this.getEquipmentSlot() == EquipmentSlot.LEGS && slotId == 37) {
				player.playSound(TheTitansNeoSounds.HARCACADIUM_HUM.get(), 5.0F, 0.0F);
				player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 199));
				player.removeEffect(MobEffects.CONFUSION);
				player.removeEffect(MobEffects.HUNGER);
				player.removeEffect(MobEffects.POISON);
			} else if (this.getEquipmentSlot() == EquipmentSlot.FEET && slotId == 36) {
				player.playSound(TheTitansNeoSounds.HARCACADIUM_HUM.get(), 5.0F, 0.5F);
				player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 19));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 39));
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == TheTitansNeoItems.ADMINIUM_HELMET.get() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == TheTitansNeoItems.ADMINIUM_CHESTPLATE.get() && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == TheTitansNeoItems.ADMINIUM_LEGGINGS.get() && player.getItemBySlot(EquipmentSlot.FEET).getItem() == TheTitansNeoItems.ADMINIUM_BOOTS.get()) {
				for (int i = 0; i < 2; i++) {
					level.addParticle(ParticleTypes.SMOKE, player.getX() + (player.getRandom().nextDouble() - 0.5D) * player.getBbWidth(), player.getY() - 1.8D + player.getRandom().nextDouble() * player.getBbHeight(), player.getZ() + (player.getRandom().nextDouble() - 0.5D) * player.getBbWidth(), 0.0D, 0.0D, 0.0D);
					level.addParticle(ParticleTypes.PORTAL, player.getX() + (player.getRandom().nextDouble() - 0.5D) * player.getBbWidth(), player.getY() - 1.8D + player.getRandom().nextDouble() * player.getBbHeight(), player.getZ() + (player.getRandom().nextDouble() - 0.5D) * player.getBbWidth(), (player.getRandom().nextDouble() - 0.5D) * 2.0D, -player.getRandom().nextDouble(), (player.getRandom().nextDouble() - 0.5D) * 2.0D);
				}
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 399));
				player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 300, 199));
				player.invulnerableDuration = 40;
				player.extinguishFire();
				player.fallDistance = 0;
				player.heal((player.getHealth() < player.getMaxHealth() / 4.0F) ? 50.0F : 10.0F);

				if (player.getY() <= -100.0D) {
					int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, Mth.floor(player.getX()), Mth.floor(player.getZ()));
					player.setPos(player.getX(), y, player.getZ());
				}
				if (player.getDeltaMovement().y > 3.0D) {
					player.setDeltaMovement(player.getDeltaMovement().x, 0.0F, player.getDeltaMovement().z);
				}
				if (player.getHealth() < player.getMaxHealth()) {
					player.addEffect(new MobEffectInstance(MobEffects.HEAL, 300, 2));
				}
				List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(10.0D, 10.0D, 10.0D));
				for (Entity radiatedEntity : entities) {
					if (radiatedEntity != null && radiatedEntity.isAlive() && entity.tickCount % 10 == 0 && radiatedEntity instanceof LivingEntity && !(radiatedEntity instanceof EntityTitan) && !(radiatedEntity instanceof AbstractGolem) && !(radiatedEntity instanceof OwnableEntity) && !(radiatedEntity instanceof Villager)) {
						LivingEntity livingEntity = (LivingEntity) radiatedEntity;
						if (!level.isClientSide()) {
							if (TheTitansNeoConfigs.adminiumArmorRadiationPlayer.get() && livingEntity instanceof Player) {
								continue;
							}
							Holder<DamageType> damageType = entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TheTitansNeoDamageTypes.RADIATION);
							livingEntity.hurt(new DamageSource(damageType), 10.0F);
							livingEntity.invulnerableTime = 0;
							livingEntity.addEffect(new MobEffectInstance(TheTitansNeoMobEffects.RADIATION, 5000, 1));
						}
					}
				}
				if (!level.isClientSide()) {
					if (TheTitansNeoConfigs.adminiumArmorExplode.get()) {
						entities = player.level().getEntities(player, player.getBoundingBox().inflate(32.0D, 8.0D, 32.0D));
						BlockPos blockPos = player.blockPosition().below();
						BlockState blockState = level.getBlockState(blockPos);
						// Block block = blockState.getBlock();
						if (!player.onGround() && blockState.isSolid()) {
							if (player.getBoundingBox() != null && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
								int minX = Mth.floor(player.getBoundingBox().minX - 3.0D);
								int minY = Mth.floor(player.getBoundingBox().minY - 1.0D);
								int minZ = Mth.floor(player.getBoundingBox().minZ - 3.0D);
								int maxX = Mth.floor(player.getBoundingBox().maxX + 3.0D);
								int maxY = Mth.floor(player.getBoundingBox().maxY + 1.0D);
								int maxZ = Mth.floor(player.getBoundingBox().maxZ + 3.0D);

								List<BlockPos> fallBlocks = new ArrayList<>();

								for (int y = minY; y <= maxY; y++) {
									for (int x = minX; x <= maxX; x++) {
										for (int z = minZ; z <= maxZ; z++) {
											BlockPos rangePos = new BlockPos(x, y, z);
											BlockState rangeState = level.getBlockState(rangePos);
											Block rangeBlock = rangeState.getBlock();

											if (player.tickCount > 5 && level.hasChunkAt(rangePos) && !rangeState.isAir()) {
												if (rangeState.getDestroySpeed(level, rangePos) >= 0.0F) {
													if (rangeBlock instanceof LiquidBlock || rangeBlock == Blocks.FIRE || rangeBlock == Blocks.COBWEB) {
														level.setBlockAndUpdate(rangePos, Blocks.AIR.defaultBlockState());
													} else if (player.getRandom().nextInt(3) == 0) {
														fallBlocks.add(rangePos.immutable());
													} else if (level.getNearestPlayer(player, 16.0D) != null) {
														level.destroyBlock(rangePos, true);
													} else {
														level.setBlockAndUpdate(rangePos, Blocks.AIR.defaultBlockState());
													}
												}
											}
										}
									}
								}

								for (BlockPos rangePos : fallBlocks) {
									BlockState rangeState = level.getBlockState(rangePos);

									if (!rangeState.isAir() && rangeState.getDestroySpeed(level, rangePos) >= 0.0F) {
										if (player.getRandom().nextDouble() < 1.0D * Math.pow(fallBlocks.size(), TheTitansNeoConfigs.titanFallBlockDecayFactor.get())) {
											FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, rangePos, rangeState);

											double d0 = (player.getBoundingBox().minX + player.getBoundingBox().maxX) / 2.0D;
											double d1 = (player.getBoundingBox().minZ + player.getBoundingBox().maxZ) / 2.0D;
											double d2 = fallingBlock.getX() - d0;
											double d3 = fallingBlock.getZ() - d1;
											double d4 = d2 * d2 + d3 * d3;

											fallingBlock.setRemainingFireTicks(10);
											if (d4 >= 0.01D) {
												double d5 = d2 / d4 * 10.0D;
												double d6 = 2.0D + player.getRandom().nextGaussian();
												double d7 = d3 / d4 * 10.0D;
												fallingBlock.push(d5, d6, d7);
											}
										}
									}
								}
								fallBlocks.clear();

								level.explode(player, player.getX(), player.getY() - 2.0D, player.getZ(), 3.0F, Level.ExplosionInteraction.MOB);
							}
							for (Entity hurtEntity : entities) {
								if (hurtEntity != null && hurtEntity instanceof LivingEntity && !(hurtEntity instanceof EntityTitan) && !(hurtEntity instanceof AbstractGolem) && !(hurtEntity instanceof OwnableEntity) && !(hurtEntity instanceof Villager)) {
									LivingEntity livingEntity = (LivingEntity) hurtEntity;
									livingEntity.hurt(livingEntity.damageSources().explosion(livingEntity, player), 300.0F);
									livingEntity.push(0.0D, 1.0D, 0.0D);
								}
							}
						}
					}
					if (TheTitansNeoConfigs.adminiumArmorGravity.get()) {
						entities = player.level().getEntities(player, player.getBoundingBox().inflate(48.0D, 48.0D, 48.0D));
						for (Entity attractedEntity : entities) {
							if (attractedEntity != null && !(attractedEntity instanceof EntityTitan) && !(attractedEntity instanceof EntityItemTitan) && !(attractedEntity instanceof EntityHarcadiumArrow)) {
								double d0 = (player.getX() - attractedEntity.getX()) / 48.0D;
								double d1 = (player.getY() + 1.0D - attractedEntity.getY()) / 48.0D;
								double d2 = (player.getZ() - attractedEntity.getZ()) / 48.0D;
								double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
								double d4 = 1.0D - d3;
								if (d4 > 0.0D) {
									d4 *= d4;
									double d5 = d0 / d3 * d4 * 0.1D;
									double d6 = d1 / d3 * d4 * 0.1D;
									double d7 = d2 / d3 * d4 * 0.1D;
									attractedEntity.push(d5, d6, d7);
								}
							}
						}
					}
				}
				player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
			} else {
				player.invulnerableDuration = 20;
				player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
			}
		}
	}
}
