package net.byAqua3.thetitansneo.event;

import java.util.ArrayList;
import java.util.List;

import net.byAqua3.thetitansneo.damage.DamageSourceTitanAttack;
import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.projectile.IEntityProjectileTitan;
import net.byAqua3.thetitansneo.entity.titan.EntityEnderColossus;
import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEnchantments;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoMobEffects;
import net.byAqua3.thetitansneo.loader.TheTitansNeoTriggers;
import net.byAqua3.thetitansneo.util.ItemUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.ExplosionKnockbackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class TheTitansNeoEvent {

	@SubscribeEvent
	public void onLivingTick(EntityTickEvent.Post event) {
		Level level = event.getEntity().level();

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (player instanceof ServerPlayer) {
				ServerPlayer serverPlayer = (ServerPlayer) player;
				TheTitansNeoTriggers.ROOT.get().trigger(serverPlayer);
			}

			if (ItemUtils.hasItem(player.getInventory(), TheTitansNeoItems.ULTIMA_BLADE.get()) && ItemUtils.hasItem(player.getInventory(), TheTitansNeoItems.OPTIMA_AXE.get())) {
				boolean flag = true;

				List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(200.0D, 200.0D, 200.0D));
				for (Entity entity : entities) {
					if (entity != null && entity.isAlive() && entity instanceof EntityEnderColossus) {
						flag = false;
					}
				}

				if (flag && !level.isClientSide()) {
					ServerLevel serverLevel = (ServerLevel) level;
					serverLevel.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(serverLevel.getRandom()), true, true);
				}
				if (level.random.nextInt(60) == 0) {
					for (Entity entity : entities) {
						if (entity != null && entity instanceof LivingEntity) {
							if (entity.isAlive()) {
								EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(level, level.getRandom().nextFloat(), level.getRandom().nextFloat(), level.getRandom().nextFloat());
								colorLightningBolt.setPos(entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ());

								if (!level.isClientSide()) {
									level.addFreshEntity(colorLightningBolt);
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void onLivingHurt(LivingDamageEvent.Post event) {
		DamageSource damageSource = event.getSource();
		Entity entity = damageSource.getEntity();

		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack itemStack = player.getMainHandItem();
			if (!itemStack.isEmpty()) {
				int a = EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.KNOCK_UP), itemStack);
				if (a > 0) {
					event.getEntity().setOnGround(false);
					event.getEntity().push(0.0D, 1.0D * a, 0.0D);
				}
				int b = EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().holderOrThrow(TheTitansNeoEnchantments.TITAN_KILLER), itemStack);
				if (b > 0 && !(damageSource instanceof DamageSourceTitanAttack)) {
					if (event.getEntity() instanceof EntityTitan) {
						EntityTitan titan = (EntityTitan) event.getEntity();
						titan.hurt(new DamageSourceTitanAttack(player), b * 100.0F);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
		Level level = event.getLevel();
		Player player = event.getEntity();
		BlockPos blockPos = event.getPos();
		BlockState blockState = level.getBlockState(blockPos);
		Block block = blockState.getBlock();
		InteractionHand hand = event.getHand();
		ItemStack itemStack = event.getItemStack();
		Item item = itemStack.getItem();
		Tool tool = itemStack.get(DataComponents.TOOL);

		if (ItemUtils.isAdminiumTool(item)) {
			if (!level.isClientSide() && !player.isCreative()) {
				List<ItemStack> drops = new ArrayList<>();
				List<ItemStack> blockDrops = Block.getDrops(blockState, (ServerLevel) level, blockPos, null);
				if (!blockDrops.isEmpty()) {
					drops.addAll(blockDrops);
				} else {
					drops.add(new ItemStack(block));
				}
				level.destroyBlock(blockPos, false);

				if (!drops.isEmpty()) {
					for (ItemStack stack : drops) {
						ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
						itemEntity.setDefaultPickUpDelay();
						level.addFreshEntity(itemEntity);
					}
				}
				itemStack.hurtAndBreak(tool.damagePerBlock(), player, LivingEntity.getSlotForHand(hand));
			}
		} else if (ItemUtils.isUltimateTool(item)) {
			itemStack.mineBlock(level, blockState, blockPos, player);
		}
	}

	@SubscribeEvent
	public void onPlayerHeartType(PlayerHeartTypeEvent event) {
		Player player = event.getEntity();

		if (player.hasEffect(TheTitansNeoMobEffects.RADIATION)) {
			event.setType(Gui.HeartType.POISIONED);
		} else if (player.hasEffect(TheTitansNeoMobEffects.ADVANCED_WITHER)) {
			event.setType(Gui.HeartType.WITHERED);
		}
	}

	@SubscribeEvent
	public void onExplosionKnockback(ExplosionKnockbackEvent event) {
		Entity entity = event.getAffectedEntity();

		if (entity instanceof IEntityProjectileTitan) {
			IEntityProjectileTitan projectileTitan = (IEntityProjectileTitan) entity;
			if (!projectileTitan.canExplosionKnockback()) {
				event.setKnockbackVelocity(new Vec3(0.0D, 0.0D, 0.0D));
			}
		}
	}
}
