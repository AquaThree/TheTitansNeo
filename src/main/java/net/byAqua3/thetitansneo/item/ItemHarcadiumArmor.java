package net.byAqua3.thetitansneo.item;

import net.byAqua3.thetitansneo.loader.TheTitansNeoAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class ItemHarcadiumArmor extends ArmorItem {

	public ItemHarcadiumArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties.attributes(createAttributes(type)));
	}

	public static ItemAttributeModifiers createAttributes(Type type) {
		ItemAttributeModifiers.Builder itemAttributeModifiers$builder = ItemAttributeModifiers.builder();
		if (type == Type.HELMET) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 5.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 50.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 9.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.CHESTPLATE) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 8.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 80.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 15.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.LEGGINGS) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 7.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 70.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 12.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		} else if (type == Type.BOOTS) {
			itemAttributeModifiers$builder.add(TheTitansNeoAttributes.TITAN_RESISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 4.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 40.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
			itemAttributeModifiers$builder.add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("armor." + type.getName()), 8.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(type.getSlot()));
		}
		return itemAttributeModifiers$builder.build();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (this.getEquipmentSlot() == EquipmentSlot.HEAD && slotId == 39) {
				player.removeEffect(MobEffects.BLINDNESS);
				player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0));
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0));
			} else if (this.getEquipmentSlot() == EquipmentSlot.CHEST && slotId == 38) {
				player.removeEffect(MobEffects.WEAKNESS);
				player.removeEffect(MobEffects.DIG_SLOWDOWN);
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 3));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 3));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 9));
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0));
			} else if (this.getEquipmentSlot() == EquipmentSlot.LEGS && slotId == 37) {
				player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 10));
				player.removeEffect(MobEffects.CONFUSION);
				player.removeEffect(MobEffects.HUNGER);
				player.removeEffect(MobEffects.POISON);
			} else if (this.getEquipmentSlot() == EquipmentSlot.FEET && slotId == 36) {
				player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 3));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 3));
			}
		}
	}

}
