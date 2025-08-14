package net.byAqua3.thetitansneo.animation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Transform {

	public float rotationX;

	public float rotationY;

	public float rotationZ;

	public float offsetX;

	public float offsetY;

	public float offsetZ;

	public Transform() {
		this.rotationX = this.rotationY = this.rotationZ = 0.0F;
		this.offsetX = this.offsetY = this.offsetZ = 0.0F;
	}

	public Transform(float rotationX, float rotationY, float rotationZ) {
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
		this.offsetX = this.offsetY = this.offsetZ = 0.0F;
	}

	public Transform(float x, float y, float z, float rotationX, float rotationY, float rotationZ) {
		this(rotationX, rotationY, rotationZ);
		this.offsetX = x;
		this.offsetY = y;
		this.offsetZ = z;
	}

	public void addRotation(float x, float y, float z) {
		this.rotationX += x;
		this.rotationY += y;
		this.rotationZ += z;
	}

	public void addOffset(float x, float y, float z) {
		this.offsetX += x;
		this.offsetY += y;
		this.offsetZ += z;
	}

}
