package net.byAqua3.thetitansneo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

@Mixin({ RangedAttribute.class })
public abstract class MixinRangedAttribute {

	@Accessor("minValue")
	@Mutable
	public abstract void setMinValue(double minValue);

	@Accessor("maxValue")
	@Mutable
	public abstract void setMaxValue(double maxValue);

	@Inject(method = "<init>", at = @At("TAIL"))
	public void injected(CallbackInfo callbackInfo) {
		RangedAttribute attribute = (RangedAttribute) (Object) this;
		if (attribute.getDescriptionId() == "attribute.name.generic.max_health") {
			this.setMaxValue(Double.MAX_VALUE);
		} else if (attribute.getDescriptionId() == "attribute.name.generic.armor") {
			this.setMaxValue(Double.MAX_VALUE);
		}
	}

}
