package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ItemWitherTurret extends Item {

	public ItemWitherTurret(Properties properties) {
		super(properties.stacksTo(1));
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
        return true;
    }
	
	@Override
    public int getEnchantmentValue() {
        return 50;
    }
	
	public EntityWitherTurret getEntity(Level level) {
		return new EntityWitherTurret(level);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		ItemStack itemStack = context.getItemInHand();
		BlockPos blockPos = context.getClickedPos();
		EntityWitherTurret entity = this.getEntity(level);
		entity.setPos(blockPos.getX() + 0.5D, blockPos.getY() + 1.0D, blockPos.getZ() + 0.5D);
		entity.setPlayerCreated(true);

		int a = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(TheTitansNeoEnchantments.HEALING), itemStack);
		if (a > 0) {
			entity.durabilityLevel = a;
		}
		int b = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(TheTitansNeoEnchantments.DAMAGE), itemStack);
		if (b > 0) {
			entity.ferocityLevel = b;
		}
		int c = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(TheTitansNeoEnchantments.SHOOTING_SPEED), itemStack);
		if (c > 0) {
			entity.maniacLevel = c;
		}
		int d = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(TheTitansNeoEnchantments.EXPLOSIVE_POWER), itemStack);
		if (d > 0) {
			entity.unstabilityLevel = d;
		}
		int e = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(TheTitansNeoEnchantments.SKULL_SPEED), itemStack);
		if (e > 0) {
			entity.shurakinLevel = e;
		}
		int f = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.UNBREAKING), itemStack);
		if (f > 0) {
			entity.unbreakingLevel = f;
		}
		int g = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(TheTitansNeoEnchantments.TITAN_KILLER), itemStack);
		if (g > 0) {
			entity.titanKillerLevel = g;
		}
		
		for (int i = 0; i < 500; i++) {
			level.addParticle(ParticleTypes.SMOKE, entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * entity.getBbWidth() * 3.0D, entity.getY() + entity.getRandom().nextDouble() * (entity.getBbHeight() + 1.0D), entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * entity.getBbWidth() * 3.0D, 0.0D, 0.0D, 0.0D);
		}
		
		if (!level.isClientSide()) {
			level.addFreshEntity(entity);
		}
		if (!player.isCreative()) {
			itemStack.shrink(1);
		}
		return InteractionResult.SUCCESS;
	}

}
