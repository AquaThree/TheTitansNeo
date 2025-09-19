package net.byAqua3.thetitansneo.entity.projectile;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.byAqua3.thetitansneo.entity.titan.EntitySpiderTitan;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EntityWebShot extends Projectile implements IEntityProjectileTitan {

	@Nullable
	private IntOpenHashSet piercingIgnoreEntityIds;

	@Nullable
	private BlockState lastState;
	protected boolean inGround;
	protected int inGroundTime;
	public int shakeTime;
	private int life;

	public EntityWebShot(EntityType<? extends EntityWebShot> entityType, Level level) {
		super(entityType, level);
	}

	public EntityWebShot(Level level) {
		super(TheTitansNeoEntities.WEB_SHOT.get(), level);
	}

	@Override
	protected void defineSynchedData(Builder builder) {

	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.life = tag.getShort("life");
		if (tag.contains("inBlockState", 10)) {
			this.lastState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("inBlockState"));
		}

		this.shakeTime = tag.getByte("shake") & 255;
		this.inGround = tag.getBoolean("inGround");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putShort("life", (short) this.life);
		if (this.lastState != null) {
			tag.put("inBlockState", NbtUtils.writeBlockState(this.lastState));
		}

		tag.putByte("shake", (byte) this.shakeTime);
		tag.putBoolean("inGround", this.inGround);
	}

	@Override
	protected double getDefaultGravity() {
		return 0.05D;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	private boolean shouldFall() {
		return this.inGround && this.level().noCollision(new AABB(this.position(), this.position()).inflate(0.06));
	}

	private void startFalling() {
		this.inGround = false;
		Vec3 vec3 = this.getDeltaMovement();
		this.setDeltaMovement(vec3.multiply((double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F)));
		this.life = 0;
	}

	@Override
	public void move(MoverType type, Vec3 pos) {
		super.move(type, pos);
		if (type != MoverType.SELF && this.shouldFall()) {
			this.startFalling();
		}
	}

	protected void tickDespawn() {
		this.life++;
		if (this.life >= 600) {
			this.discard();
		}
	}
	
	@Override
    protected boolean canHitEntity(Entity entity) {
		return this.getOwner() != null && entity == this.getOwner() ? false : super.canHitEntity(entity);
	}

	@Nullable
	protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
		return ProjectileUtil.getEntityHitResult(this.level(), this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), this::canHitEntity);
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		this.lastState = this.level().getBlockState(result.getBlockPos());
		super.onHitBlock(result);
		Vec3 vec3 = result.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		this.setDeltaMovement(vec3);

		Vec3 vec31 = vec3.normalize().scale(0.05F);
		this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
		this.inGround = true;
		this.shakeTime = 7;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (result.getEntity() instanceof LivingEntity && this.getOwner() instanceof EntitySpiderTitan) {
			LivingEntity livingEntity = (LivingEntity) result.getEntity();
			EntitySpiderTitan spiderTitan = (EntitySpiderTitan) this.getOwner();

			float amount = (float) spiderTitan.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
			int knockbackAmount = spiderTitan.getKnockbackAmount();

			spiderTitan.attackEntity(livingEntity, amount);
			livingEntity.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D, knockbackAmount * 0.2D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * knockbackAmount) * 0.2D);

			int i11 = Mth.floor(this.getX());
			int i1 = Mth.floor(this.getY() + 1.0D);
			int j1 = Mth.floor(this.getZ());
			for (int l1 = -2 - this.getRandom().nextInt(4); l1 <= 2 + this.getRandom().nextInt(4); l1++) {
				for (int i2 = -2 - this.getRandom().nextInt(4); i2 <= 2 + this.getRandom().nextInt(4); i2++) {
					for (int j = -2 - this.getRandom().nextInt(4); j <= 2 + this.getRandom().nextInt(4); j++) {
						int j2 = i11 + l1;
						int k = i1 + j;
						int l = j1 + i2;
						BlockPos blockPos = new BlockPos(j2, k, l);
						this.level().setBlockAndUpdate(blockPos, Blocks.COBWEB.defaultBlockState());
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.discard();
			}
		}
		super.onHitEntity(result);
	}

	@Override
	public void tick() {
		super.tick();
		this.noPhysics = true;
		this.stuckSpeedMultiplier = Vec3.ZERO;

		if (this.getOwner() != null && !this.getOwner().isAlive()) {
			if (!this.level().isClientSide()) {
				this.discard();
			}
		}

		Vec3 vec3 = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			double d0 = vec3.horizontalDistance();
			this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * 180.0F / Mth.PI));
			this.setXRot((float) (Mth.atan2(vec3.y, d0) * 180.0F / Mth.PI));
			this.yRotO = this.getYRot();
			this.xRotO = this.getXRot();
		}

		BlockPos blockpos = this.blockPosition();
		BlockState blockstate = this.level().getBlockState(blockpos);
		if (!blockstate.isAir() && !this.noPhysics) {
			VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
			if (!voxelshape.isEmpty()) {
				Vec3 vec31 = this.position();

				for (AABB aabb : voxelshape.toAabbs()) {
					if (aabb.move(blockpos).contains(vec31)) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.shakeTime > 0) {
			this.shakeTime--;
		}

		if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
			this.clearFire();
		}

		if (this.inGround) {
			this.startFalling();
			this.tickDespawn();
			if (this.lastState != blockstate && this.shouldFall()) {
				this.startFalling();
			} else if (!this.level().isClientSide()) {
				this.tickDespawn();
			}
			this.inGroundTime++;
		} else {
			this.inGroundTime = 0;
			Vec3 vec32 = this.position();
			Vec3 vec33 = vec32.add(vec3);
			HitResult hitResult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (hitResult.getType() != HitResult.Type.MISS) {
				vec33 = hitResult.getLocation();
			}
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				this.onHitBlock((BlockHitResult) hitResult);
			}

			EntityHitResult entityHitResult = this.findHitEntity(vec32, vec33);
			if (entityHitResult != null) {
				this.onHitEntity(entityHitResult);
			}

			vec3 = this.getDeltaMovement();
			double d5 = vec3.x;
			double d6 = vec3.y;
			double d1 = vec3.z;
			double d7 = this.getX() + d5;
			double d2 = this.getY() + d6;
			double d3 = this.getZ() + d1;
			double d4 = vec3.horizontalDistance();
			if (this.noPhysics) {
				this.setYRot((float) (Mth.atan2(-d5, -d1) * 180.0F / Mth.PI));
			} else {
				this.setYRot((float) (Mth.atan2(d5, d1) * 180.0F / Mth.PI));
			}

			this.setXRot((float) (Mth.atan2(d6, d4) * 180.0F / Mth.PI));
			this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
			this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
			float f = 0.99F;
			if (this.isInWater()) {
				for (int j = 0; j < 4; j++) {
					this.level().addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25F, d2 - d6 * 0.25F, d3 - d1 * 0.25F, d5, d6, d1);
				}
			}

			this.setDeltaMovement(vec3.scale(f));
			if (!this.noPhysics) {
				this.applyGravity();
			}
			this.level().addParticle(ParticleTypes.POOF, this.getX(), this.getY() + 1.5D, this.getZ(), 0.0D, 0.0D, 0.0D);

			this.setPos(d7, d2, d3);
			this.checkInsideBlocks();

			for (int l1 = -1; l1 <= 1; l1++) {
				for (int i2 = -1; i2 <= 1; i2++) {
					for (int j = -1; j <= 1; j++) {
						int j2 = (int) this.getX() + l1;
						int k = (int) this.getY() + 1 + j;
						int l = (int) this.getZ() + i2;
						BlockPos blockPos = new BlockPos(j2, k, l);
						if (this.tickCount > 10) {
							this.level().setBlockAndUpdate(blockPos, Blocks.COBWEB.defaultBlockState());
						}
					}
				}
			}
		}
	}
}
