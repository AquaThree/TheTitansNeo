package net.byAqua3.thetitansneo.item;

import java.util.List;

import net.byAqua3.thetitansneo.entity.titan.EntityTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoAttributes;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class ItemVoidArmor extends ArmorItem {

	public ItemVoidArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties.attributes(createAttributes(type)));
	}

	public static ItemAttributeModifiers createAttributes(Type type) {
		ItemAttributeModifiers.Builder itemAttributeModifiers$builder = ItemAttributeModifiers.builder();
		if (type == Type.HELMET) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 8.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 250.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 11.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.CHESTPLATE) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 14.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 400.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 17.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.LEGGINGS) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 12.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 350.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 13.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.BOOTS) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 6.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 200.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 9.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		}
		return itemAttributeModifiers$builder.build();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);

		if (entity instanceof Player) {
			Player player = (Player) entity;
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
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 49));
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
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 5));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 19));
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == TheTitansNeoItems.VOID_HELMET.get() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == TheTitansNeoItems.VOID_CHESTPLATE.get() && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == TheTitansNeoItems.VOID_LEGGINGS.get() && player.getItemBySlot(EquipmentSlot.FEET).getItem() == TheTitansNeoItems.VOID_BOOTS.get()) {
				for (int i = 0; i < 4; i++) {
					level.addParticle(ParticleTypes.MYCELIUM, player.getX() + (player.getRandom().nextDouble() - 0.5D) * player.getBbWidth() * 2.0D, player.getY() - 1.75D + player.getRandom().nextDouble() * player.getBbHeight(), player.getZ() + (player.getRandom().nextDouble() - 0.5D) * player.getBbWidth() * 2.0D, 0.0D, 0.05D, 0.0D);
				}
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 199));
				player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 300, 99));
				player.fallDistance = 0;

				List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(4.0D, 4.0D, 4.0D));
				for (Entity entity1 : entities) {
					if (entity1 != null && entity1 instanceof LivingEntity && !(entity1 instanceof EntityTitan) && !(entity1 instanceof AbstractGolem) && !(entity1 instanceof OwnableEntity) && !(entity1 instanceof Villager)) {
						LivingEntity livingEntity = (LivingEntity) entity1;
						if (!level.isClientSide()) {
							if (TheTitansNeoConfigs.voidArmorRadiationPlayer.get() && livingEntity instanceof Player) {
								continue;
							}
							livingEntity.hurt(livingEntity.damageSources().fellOutOfWorld(), 4.0F);
							livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5000, 1));
							livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5000, 1));
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5000, 9));
							livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 5000, 3));
						}
					}
				}
			}
		}
	}

}
