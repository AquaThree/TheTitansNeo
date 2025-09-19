package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.entity.projectile.EntityGrowthSerum;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemGrowthSerum extends Item {

	public ItemGrowthSerum(Properties properties) {
		super(properties.stacksTo(16));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (!player.isCreative()) {
			itemStack.shrink(1);
		}
		level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 0.8F));
		EntityGrowthSerum growthSerum = new EntityGrowthSerum(level);
		growthSerum.setPos(player.getX(), player.getEyeY(), player.getZ());
		growthSerum.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
		if (!level.isClientSide()) {
			level.addFreshEntity(growthSerum);
		}
		return super.use(level, player, hand);
	}

}
