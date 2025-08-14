package net.byAqua3.thetitansneo.entity;

public interface IEntityAnimatedHealth {

	float getAnimatedHealth();

	void setAnimatedHealth(float animatedHealth);

	default void updateAnimatedHealth(float health, float maxHealth) {
		if (this.getAnimatedHealth() <= maxHealth && this.getAnimatedHealth() > health) {
			float factor = (this.getAnimatedHealth() - health) * 0.1F;
			if (factor < 0.1F) {
				factor = 0.1F;
			}
			this.setAnimatedHealth(this.getAnimatedHealth() - factor);
			if (this.getAnimatedHealth() <= 0.0F) {
				this.setAnimatedHealth(0.0F);
			}
		} else {
			if (this.getAnimatedHealth() > maxHealth) {
				this.setAnimatedHealth(maxHealth);
			} else {
				this.setAnimatedHealth(health);
			}
		}
	}
}
