package net.byAqua3.thetitansneo.loader;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum TheTitansNeoTiers implements Tier {

	COPPER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 80, 3.0F, -1.0F, 12, Ingredient.EMPTY),
	TIN(BlockTags.INCORRECT_FOR_STONE_TOOL, 100, 3.0F, -1.0F, 18, Ingredient.EMPTY),
	BRONZE(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 6.0F, -1.0F, 14, Ingredient.EMPTY),
	STEEL(BlockTags.NEEDS_DIAMOND_TOOL, 900, 8.0F, -1.0F, 10, Ingredient.EMPTY),
	HARCADIUM(TheTitansNeoTags.INCORRECT_FOR_HARCADIUM_TOOL, 75000, 120.0F, -1.0F, 30, Ingredient.EMPTY),
	VOID(TheTitansNeoTags.INCORRECT_FOR_HARCADIUM_TOOL, 1000000, 4000.0F, -1.0F, 40, Ingredient.EMPTY),
	ADMINIUM(TheTitansNeoTags.INCORRECT_FOR_HARCADIUM_TOOL, 2147483647, 1000000000.0F, -1.0F, 60, Ingredient.EMPTY),
	ULTIMA(TheTitansNeoTags.INCORRECT_FOR_ULTIMA_TOOL, 2147483647, 9999.0F, -1.0F, 0, Ingredient.EMPTY);

	private final TagKey<Block> incorrectBlocksForDrops;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private Ingredient repairIngredient;

	private TheTitansNeoTiers(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float damage, int enchantmentValue, Ingredient repairIngredient) {
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.damage;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return this.incorrectBlocksForDrops;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient;
	}
	
	public void setRepairIngredient(Ingredient repairIngredient) {
		this.repairIngredient = repairIngredient;
	}
}
