package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.projectile.EntityHarcadiumArrow;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.util.ItemUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ItemHarcadiumBow extends BowItem {

	public ItemHarcadiumBow(Properties properties) {
		super(properties.durability(12001));
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		InteractionResultHolder<ItemStack> ret = net.neoforged.neoforge.event.EventHooks.onArrowNock(stack, level, player, hand, true);
		if (ret != null) {
			return ret;
		}
		boolean flag = (player.isCreative() || EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.INFINITY), stack) > 0);

		if (flag || ItemUtils.hasItem(player.getInventory().items, TheTitansNeoItems.HARCADIUM_ARROW.get()) || ItemUtils.hasItem(player.getInventory().offhand, TheTitansNeoItems.HARCADIUM_ARROW.get())) {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.pass(stack);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int time) {
		Player player = (Player) entity;
		boolean flag = (player.isCreative() || EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.INFINITY), stack) > 0);

		if (flag || ItemUtils.hasItem(player.getInventory().items, TheTitansNeoItems.HARCADIUM_ARROW.get()) || ItemUtils.hasItem(player.getInventory().offhand, TheTitansNeoItems.HARCADIUM_ARROW.get())) {
			int max = this.getUseDuration(stack, entity);
			int j = max - time;

			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1) {
				return;
			}

			if (f > 1.0) {
				f = 1.0F;
			}

			EntityHarcadiumArrow arrow = new EntityHarcadiumArrow(level);
			arrow.setPos(player.getX(), player.getEyeY(), player.getZ());
			arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
			arrow.setBaseDamage(30.0D);
			arrow.setOwner(player);

			if (f == 1.0F) {
				arrow.setCritArrow(true);
			}

			int k = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.POWER), stack);
			if (k > 0) {
				arrow.setBaseDamage(arrow.getBaseDamage() + k * 100.0D);
			}

			int l = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.PUNCH), stack);
			if (l > 0) {
				arrow.setKnockback(l * 3);
			}

			if (EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.FLAME), stack) > 0) {
				arrow.igniteForSeconds(500);
			}
			stack.hurtAndBreak(this.getDurabilityUse(stack), player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
			if (flag) {
				arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			} else {
				if (ItemUtils.hasItem(player.getInventory().offhand, TheTitansNeoItems.HARCADIUM_ARROW.get())) {
					for (ItemStack itemStack : player.getInventory().offhand) {
						if (itemStack.is(TheTitansNeoItems.HARCADIUM_ARROW.get())) {
							itemStack.shrink(1);
							break;
						}
					}
				} else {
					for (ItemStack itemStack : player.getInventory().items) {
						if (itemStack.is(TheTitansNeoItems.HARCADIUM_ARROW.get())) {
							itemStack.shrink(1);
							break;
						}
					}
				}
			}

			if (!level.isClientSide()) {
				level.addFreshEntity(arrow);
			}

			level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 0.5F);

			player.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
		return new EntityHarcadiumArrow(arrow.level());
	}

}
