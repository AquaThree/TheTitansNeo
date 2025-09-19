package net.byAqua3.thetitansneo.animation;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Animator {

	private int tempTick;
	private int prevTempTick;

	private boolean correctAnim;

	private ModelPart rootModel;

	private IAnimatedEntity animEntity;

	private HashMap<ModelPart, Transform> transformMap;
	private HashMap<ModelPart, Transform> prevTransformMap;

	public Animator(ModelPart rootModel) {
		this.tempTick = this.prevTempTick = 0;
		this.correctAnim = false;
		this.rootModel = rootModel;
		this.transformMap = new HashMap<ModelPart, Transform>();
		this.prevTransformMap = new HashMap<ModelPart, Transform>();
	}

	public IAnimatedEntity getEntity() {
		return this.animEntity;
	}

	public void update(IAnimatedEntity entity) {
		this.tempTick = this.prevTempTick = 0;
		this.correctAnim = false;
		this.animEntity = entity;
		this.transformMap.clear();
		this.prevTransformMap.clear();
		for (int i = 0; i < this.rootModel.getAllParts().toList().size(); i++) {
			ModelPart part = this.rootModel.getAllParts().toList().get(i);
			part.xRot = 0.0F;
			part.yRot = 0.0F;
			part.zRot = 0.0F;
		}
	}

	public boolean setAnimationID(int id) {
		this.tempTick = this.prevTempTick = 0;
		this.correctAnim = (this.animEntity.getAnimationID() == id);
		return this.correctAnim;
	}

	public void startPhase(int duration) {
		if (!this.correctAnim) {
			return;
		}
		this.prevTempTick = this.tempTick;
		this.tempTick += duration;
	}

	public void setStationaryPhase(int duration) {
		this.startPhase(duration);
		this.endPhase(true);
	}

	public void resetPhase(int duration) {
		this.startPhase(duration);
		this.endPhase();
	}

	public void rotate(ModelPart part, float x, float y, float z) {
		if (!this.correctAnim) {
			return;
		}
		if (!this.transformMap.containsKey(part)) {
			this.transformMap.put(part, new Transform(x, y, z));
		} else {
			this.transformMap.get(part).addRotation(x, y, z);
		}
	}

	public void move(ModelPart part, float x, float y, float z) {
		if (!this.correctAnim) {
			return;
		}
		if (!this.transformMap.containsKey(part)) {
			this.transformMap.put(part, new Transform(x, y, z, 0.0F, 0.0F, 0.0F));
		} else {
			this.transformMap.get(part).addOffset(x, y, z);
		}
	}

	public void endPhase() {
		this.endPhase(false);
	}

	private void endPhase(boolean stationary) {
		if (!this.correctAnim)
			return;
		int animTick = this.animEntity.getAnimationTick();
		if (animTick >= this.prevTempTick && animTick < this.tempTick)
			if (stationary) {
				for (ModelPart part : this.prevTransformMap.keySet()) {
					Transform transform = this.prevTransformMap.get(part);
					part.xRot += transform.rotationX;
					part.yRot += transform.rotationY;
					part.zRot += transform.rotationZ;
					part.x += transform.offsetX;
					part.y += transform.offsetY;
					part.z += transform.offsetZ;
				}
			} else {
				float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
				float tick = ((animTick - this.prevTempTick) + partialTicks)
						/ (this.tempTick - this.prevTempTick);
				float inc = (float) Math.sin((float) (tick * Math.PI / 2.0F)), dec = 1.0F - inc;
				for (ModelPart part : this.prevTransformMap.keySet()) {
					Transform transform = this.prevTransformMap.get(part);
					part.xRot += dec * transform.rotationX;
					part.yRot += dec * transform.rotationY;
					part.zRot += dec * transform.rotationZ;
					part.x += dec * transform.offsetX;
					part.y += dec * transform.offsetY;
					part.z += dec * transform.offsetZ;
				}
				for (ModelPart part : this.transformMap.keySet()) {
					Transform transform = this.transformMap.get(part);
					part.xRot += inc * transform.rotationX;
					part.yRot += inc * transform.rotationY;
					part.zRot += inc * transform.rotationZ;
					part.x += inc * transform.offsetX;
					part.y += inc * transform.offsetY;
					part.z += inc * transform.offsetZ;
				}
			}
		if (!stationary) {
			this.prevTransformMap.clear();
			this.prevTransformMap.putAll(this.transformMap);
			this.transformMap.clear();
		}
	}
}
