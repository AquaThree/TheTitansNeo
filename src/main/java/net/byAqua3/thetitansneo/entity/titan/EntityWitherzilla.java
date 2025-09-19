package net.byAqua3.thetitansneo.entity.titan;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.neoforge.event.EventHooks;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import javax.annotation.Nullable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.byAqua3.thetitansneo.loader.TheTitansNeoItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.byAqua3.thetitansneo.loader.TheTitansNeoBlocks;
import net.byAqua3.thetitansneo.loader.TheTitansNeoConfigs;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.byAqua3.thetitansneo.loader.TheTitansNeoDimensions;
import net.minecraft.util.Mth;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.byAqua3.thetitansneo.TheTitansNeo;
import net.byAqua3.thetitansneo.entity.EntityColorLightningBolt;
import net.byAqua3.thetitansneo.entity.EntityWitherTurret;
import net.byAqua3.thetitansneo.entity.IBossBarDisplay;
import net.byAqua3.thetitansneo.entity.PredicateTitanTarget;
import net.byAqua3.thetitansneo.entity.ai.EntityAINearestTargetTitan;
import net.byAqua3.thetitansneo.entity.minion.EntityWitherzillaMinion;
import net.byAqua3.thetitansneo.item.ItemOptimaAxe;
import net.byAqua3.thetitansneo.item.ItemUltimaBlade;
import net.byAqua3.thetitansneo.item.spawnegg.ItemTitanSpawnEgg;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.byAqua3.thetitansneo.loader.TheTitansNeoEntities;
import net.byAqua3.thetitansneo.loader.TheTitansNeoSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import java.util.List;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class EntityWitherzilla extends EntityTitan implements RangedAttackMob, IBossBarDisplay {

	private static final EntityDataAccessor<Integer> DATA_TARGET_A = SynchedEntityData.defineId(EntityWitherzilla.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_TARGET_B = SynchedEntityData.defineId(EntityWitherzilla.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_TARGET_C = SynchedEntityData.defineId(EntityWitherzilla.class, EntityDataSerializers.INT);
	private static final List<EntityDataAccessor<Integer>> DATA_TARGETS = ImmutableList.of(DATA_TARGET_A, DATA_TARGET_B, DATA_TARGET_C);

	private float[] xRotHeads;
	private float[] yRotHeads;
	private float[] xRotOHeads;
	private float[] yRotOHeads;
	private int[] nextHeadUpdate;
	private int[] idleHeadUpdates;
	private int blockBreakCounter;
	public int affectTicks;
	private int attackTimer;
	public int omegaCounter;

	public EntityWitherzilla(EntityType<? extends EntityTitan> entityType, Level level) {
		super(entityType, level);
		this.xRotHeads = new float[2];
		this.yRotHeads = new float[2];
		this.xRotOHeads = new float[2];
		this.yRotOHeads = new float[2];
		this.nextHeadUpdate = new int[2];
		this.idleHeadUpdates = new int[2];
		this.noPhysics = true;
		this.playSound(TheTitansNeoSounds.WITHERZILLA_SPAWN.get(), Float.MAX_VALUE, 1.0f);
	}

	public EntityWitherzilla(Level level) {
		this(TheTitansNeoEntities.WITHERZILLA.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return EntityTitan.createAttributes().add(Attributes.MAX_HEALTH, 20000000.0D).add(Attributes.ATTACK_DAMAGE, Float.MAX_VALUE).add(Attributes.MOVEMENT_SPEED, 0.5D);
	}

	@Override
	public ResourceLocation getBossBarTexture() {
		return ResourceLocation.tryBuild(TheTitansNeo.MODID, "textures/gui/bossbar/witherzilla.png");
	}

	@Override
	public int getBossBarNameColor() {
		return 15728880 - (int) (Mth.cos(this.tickCount * 0.05F) * 1.572888E7F);
	}

	@Override
	public int getBossBarHealthColor() {
		return 15728880 - (int) (Mth.cos(this.tickCount * 0.05F) * 1.572888E7F);
	}

	@Override
	public int getBossBarWidth() {
		return 185;
	}

	@Override
	public int getBossBarHeight() {
		return 32;
	}

	@Override
	public int getBossBarInterval() {
		return 5;
	}

	@Override
	public int getBossBarVOffset() {
		return 0;
	}

	@Override
	public int getBossBarVHeight() {
		return 0;
	}

	@Override
	public int getBossBarTextOffset() {
		return 7;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> true);
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.targetSelector.addGoal(0, new EntityAINearestTargetTitan<LivingEntity>(this, LivingEntity.class, 0, false, false, PredicateTitanTarget.WitherzillaSorter));
	}

	public int getAlternativeTarget(int head) {
		return this.entityData.get(DATA_TARGETS.get(head));
	}

	public void setAlternativeTarget(int targetOffset, int newId) {
		this.entityData.set(DATA_TARGETS.get(targetOffset), newId);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_TARGET_A, 0);
		builder.define(DATA_TARGET_B, 0);
		builder.define(DATA_TARGET_C, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	public boolean isInOmegaForm() {
		return this.level().dimension() != TheTitansNeoDimensions.THE_VOID;
	}

	protected double getHeadX(int head) {
		if (head <= 0) {
			return this.getX();
		}
		float f = (this.yBodyRot + 180 * (head - 1)) * 0.017453292F;
		float f2 = Mth.cos(f);
		return this.getX() + f2 * 1.3D * (this.isInOmegaForm() ? 256.0F : 128.0F);
	}

	protected double getHeadY(int head) {
		return this.getEyeY();
	}

	protected double getHeadZ(int head) {
		if (head <= 0) {
			return this.getZ();
		}
		float f = (this.yBodyRot + 180 * (head - 1)) * 0.017453292F;
		float f2 = Mth.sin(f);
		return this.getZ() + f2 * 1.3D * (this.isInOmegaForm() ? 256.0F : 128.0F);
	}

	public float getHeadXRot(int head) {
		return this.xRotHeads[head];
	}

	public float getHeadYRot(int head) {
		return this.yRotHeads[head];
	}

	private float rotlerp(float angle, float targetAngle, float max) {
		float f = Mth.wrapDegrees(targetAngle - angle);
		if (f > max) {
			f = max;
		}
		if (f < -max) {
			f = -max;
		}
		return angle + f;
	}

	private void createBeaconPortal(int x, int z) {
		int b0 = this.level().getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
		int b1 = 4;
		for (int k = b0 - 1; k <= b0 + 32; k++) {
			for (int l = x - b1; l <= x + b1; l++) {
				for (int i1 = z - b1; i1 <= z + b1; i1++) {
					double d0 = (l - x);
					double d1 = (i1 - z);
					double d2 = d0 * d0 + d1 * d1;
					if (d2 <= (b1 - 0.5D) * (b1 - 0.5D))
						if (k < b0) {
							if (d2 <= ((b1 - 1) - 0.5D) * ((b1 - 1) - 0.5D)) {
								this.level().setBlockAndUpdate(new BlockPos(l, k, i1), Blocks.BEDROCK.defaultBlockState());
							}
						} else if (k > b0) {
							this.level().setBlockAndUpdate(new BlockPos(l, k, i1), Blocks.AIR.defaultBlockState());
						} else if (d2 > ((b1 - 1) - 0.5D) * ((b1 - 1) - 0.5D)) {
							this.level().setBlockAndUpdate(new BlockPos(l, k, i1), Blocks.BEDROCK.defaultBlockState());
						} else {
							this.level().setBlockAndUpdate(new BlockPos(l, k, i1), Blocks.END_PORTAL.defaultBlockState());
						}
				}
			}
		}
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 0, z), Blocks.BEDROCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 1, z), Blocks.BEDROCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 2, z), Blocks.BEDROCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x - 1, b0 + 2, z), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.WEST));
		this.level().setBlockAndUpdate(new BlockPos(x + 1, b0 + 2, z), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.EAST));
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 2, z - 1), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.NORTH));
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 2, z + 1), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.SOUTH));
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 3, z), Blocks.BEDROCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 4, z), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x + 1, b0 + 4, z + 1), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x + 1, b0 + 4, z), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x + 1, b0 + 4, z - 1), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x - 1, b0 + 4, z + 1), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x - 1, b0 + 4, z), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x - 1, b0 + 4, z - 1), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 4, z + 1), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 4, z - 1), Blocks.DIAMOND_BLOCK.defaultBlockState());
		this.level().setBlockAndUpdate(new BlockPos(x, b0 + 5, z), Blocks.BEACON.defaultBlockState());
	}

	protected void performRangedAttack(int head, double x, double y, double z, boolean isDangerous) {
		double d0 = this.getHeadX(head);
		double d2 = this.getHeadY(head);
		double d3 = this.getHeadZ(head);
		double d4 = x - d0;
		double d5 = y - d2;
		double d6 = z - d3;
		Vec3 vec3 = new Vec3(d4, d5, d6);
		WitherSkull witherskull = new WitherSkull(this.level(), this, vec3.normalize());
		witherskull.setOwner(this);
		if (isDangerous) {
			witherskull.setDangerous(true);
		}
		witherskull.setPosRaw(d0, d2, d3);
		this.level().addFreshEntity(witherskull);
	}

	protected void performRangedAttack(int head, LivingEntity target) {
		this.performRangedAttack(head, target.getX(), target.getY() + target.getEyeHeight() * 0.5f, target.getZ(), head == 0 && this.random.nextFloat() < 0.001F);
		this.attackEntity(target, 20.0F);
		target.invulnerableTime = 0;
	}

	public void doLightningAttackTo(Entity entity) {
		if (entity != null && entity.isAlive() && !(entity instanceof EntityTitanPart)) {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				player.sendSystemMessage(Component.literal(String.valueOf(this.getRandom().nextInt(1234567890))).withStyle(ChatFormatting.OBFUSCATED));
			}
			if (entity instanceof LivingEntity && !(entity instanceof EntityTitan)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				this.attackEntity(livingEntity, 20.0F);
				EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(livingEntity.level(), this.getRandom().nextFloat(), this.getRandom().nextFloat(), this.getRandom().nextFloat());
				colorLightningBolt.setPos(entity.getX(), entity.getY(), entity.getZ());
				if (!livingEntity.level().isClientSide()) {
					this.level().addFreshEntity(colorLightningBolt);
				}
				if (livingEntity.getBbHeight() >= 6.0F || livingEntity.isInvulnerable()) {
					livingEntity.setHealth(0.0F);
					livingEntity.hurt(this.damageSources().fellOutOfWorld(), Float.MAX_VALUE);
					livingEntity.die(this.damageSources().fellOutOfWorld());
					livingEntity.discard();
				}
				if (livingEntity.getBbHeight() >= 6.0F || this.affectTicks >= 600) {
					livingEntity.setHealth(0.0F);
					for (int i = 0; i < 50; ++i) {
						this.attackEntity(livingEntity, Float.MAX_VALUE);
					}
				}
			}
			if (entity instanceof EntityTitan titan) {
				titan.setInvulTime(titan.getInvulTime() - 20);
				this.attackEntity(titan, 5000.0F);
			} else {
				entity.push(0.0D, 0.5D, 0.0D);
			}
			entity.invulnerableTime = 1;
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		return groupData;
	}

	@Override
	public Component getName() {
		if (this.tickCount % 20 < (3 + this.getRandom().nextInt(3)) || this.getInvulTime() >= 1000) {
			return Component.literal("Regnator");
		}
		return Component.translatable("entity.thetitansneo.witherzilla").withStyle(ChatFormatting.BOLD);
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		float width = this.isInOmegaForm() ? 128.0F : 64.0F;
		float height = this.isInOmegaForm() ? 448.0F : 224.0F;
		float eyeHeight = this.isInOmegaForm() ? 380.8F : 190.4F;
		return EntityDimensions.scalable(width, height).withEyeHeight(eyeHeight);
	}

	@Override
	protected void refreshAttributes() {
		if (this.isInOmegaForm()) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20000000.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Float.MAX_VALUE);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10000000.0D);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Float.MAX_VALUE);
		}
	}

	@Override
	public boolean canAttackEntity(Entity entity) {
		return !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityWitherTurret witherTurret && !witherTurret.isPlayerCreated());
	}

	@Override
	public boolean canBeHurtByPlayer() {
		return !this.isInvulnerable() && this.level().getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	public boolean shouldMove() {
		return false;
	}

	@Override
	public boolean isArmored() {
		return this.deathTicks <= 0 && this.getHealth() <= this.getMaxHealth() / 2.0f;
	}

	@Override
	public int getMinionCap() {
		return TheTitansNeoConfigs.witherzillaMinionSpawnCap.get();
	}

	@Override
	public EnumTitanStatus getTitanStatus() {
		return EnumTitanStatus.GOD;
	}

	@Override
	public int getThreashHold() {
		return 210;
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TheTitansNeoSounds.WITHERZILLA_LIVING.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return TheTitansNeoSounds.WITHERZILLA_GRUNT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TheTitansNeoSounds.WITHERZILLA_DEATH.get();
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public void setDeltaMovement(Vec3 deltaMovement) {
	}

	@Override
	public void setTitanDeltaMovement(Vec3 deltaMovement) {
	}

	@Override
	public void destroyBlocksInAABB(AABB aabb) {
		this.destroyBlocksInAABBGriefingBypass(aabb);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (amount >= 100000.0F) {
			amount = 100000.0F;
		}
		if (this.blockBreakCounter <= 0) {
			this.blockBreakCounter = 1;
		}
		this.tickCount++;
		return super.hurt(damageSource, amount);
	}

	@Override
	public void kill() {
		if (!this.level().players().isEmpty()) {
			for (Player player : this.level().players()) {
				player.sendSystemMessage(Component.translatable("entity.thetitansneo.witherzilla.killattempt"));
			}
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		if (target instanceof EntityTitan || target.getBbHeight() >= 6.0F) {
			double d0 = this.distanceToSqr(target);
			if (d0 < 1000.0 && this.attackTimer <= 0) {
				this.attackTimer = 1 + this.getRandom().nextInt(9);
				for (int i = 0; i < 10; i++) {
					this.attackEntity(target, Float.MAX_VALUE);
					if (target instanceof EntityTitan titan) {
						if (titan.getInvulTime() > 1) {
							titan.setInvulTime(titan.getInvulTime() - 20);
						}
					}
				}
				target.push((-Math.sin(this.getYRot() * Math.PI / 180.0F) * 2.0D) * 0.2D, 0.4D, (Math.cos(this.getYRot() * Math.PI / 180.0F) * 2.0D) * 0.2D);
			}
		} else {
			this.performRangedAttack(0, target);
		}
	}

	@Override
	public void aiStep() {
		this.attackTimer--;
		if (this.getInvulTime() > 0) {
			if (this.tickCount % 1 == 0) {
				this.heal(this.getMaxHealth() / 800.0F);
			}
			int i = this.getInvulTime() - 1;
			this.setInvulTime(i);
			if (i <= 0) {
				this.blockBreakCounter = 1;
				if (!this.level().players().isEmpty()) {
					for (Player player : this.level().players()) {
						this.level().playLocalSound(player.blockPosition(), TheTitansNeoSounds.WITHERZILLA_SPAWN.get(), SoundSource.MASTER, 10.0F, 1.0F, false);
					}
				}
			}
		} else {
			super.aiStep();

			if (this.isInOmegaForm()) {
				this.omegaCounter = 600;
				if (this.tickCount % 400 == 0) {
					Player player = this.level().getNearestPlayer(this, -1.0D);
					if (player != null && !player.getAbilities().invulnerable) {
						this.setTarget(player);
					}
				}
			}
			for (int i = 1; i < 3; i++) {
				if (this.tickCount >= this.nextHeadUpdate[i - 1] && this.getTarget() != null) {
					this.nextHeadUpdate[i - 1] = this.tickCount + this.random.nextInt(20);
					if (this.idleHeadUpdates[i - 1]++ > 15) {
						for (int i2 = 0; i2 < 100; i2++) {
							float f = 100.0f;
							float f2 = 20.0f;
							double d0 = Mth.nextDouble(this.getRandom(), this.getX() - f, this.getX() + f);
							double d2 = Mth.nextDouble(this.getRandom(), this.getY() - f2, this.getY() + f2);
							double d3 = Mth.nextDouble(this.getRandom(), this.getZ() - f, this.getZ() + f);
							this.performRangedAttack(i + 1, d0, d2, d3, true);
						}
						this.idleHeadUpdates[i - 1] = 0;
					}
					int k = this.getAlternativeTarget(i);
					if (k > 0) {
						LivingEntity livingEntity = (LivingEntity) this.level().getEntity(k);
						if (livingEntity != null && livingEntity.isAlive()) {
							this.performRangedAttack(i + 1, livingEntity);
							this.nextHeadUpdate[i - 1] = this.tickCount;
							this.idleHeadUpdates[i - 1] = 0;
						} else {
							this.setAlternativeTarget(i, 0);
						}
					} else if (this.getTarget() != null && this.getTarget().isAlive()) {
						this.setAlternativeTarget(i, this.getTarget().getId());
					} else {
						this.setAlternativeTarget(i, 0);
					}
				}
			}
			if (this.getTarget() != null) {
				this.setAlternativeTarget(0, this.getTarget().getId());
			} else {
				this.setAlternativeTarget(0, 0);
			}
			if (this.blockBreakCounter > 0) {
				this.blockBreakCounter--;
				if (this.blockBreakCounter == 0 && EventHooks.canEntityGrief(this.level(), this)) {
					boolean flag = false;
					int l = Mth.floor(32.0F);
					for (BlockPos blockPos : BlockPos.betweenClosed(this.getBlockX() - l, this.getBlockY() - 32, this.getBlockZ() - l, this.getBlockX() + l, this.getBlockY() + 246, this.getBlockZ() + l)) {
						BlockState blockState = this.level().getBlockState(blockPos);
						if (blockState.canEntityDestroy(this.level(), blockPos, (Entity) this) && EventHooks.onEntityDestroyBlock(this, blockPos, blockState)) {
							flag = (this.level().destroyBlock(blockPos, false, this) || flag);
						}
					}
					if (flag) {
						this.level().levelEvent(null, 1022, this.blockPosition(), 0);
						this.destroyBlocksInAABB(this.getBoundingBox());
					}
				}
			}
		}
	}

	@Override
	protected void dropAllItem() {
		for (int x = 0; x < 250; x++) {
			EntityExperienceOrbTitan experienceOrbTitan = new EntityExperienceOrbTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), 32000);
			experienceOrbTitan.push(0.0D, 1.0D, 0.0D);
			if (!this.level().isClientSide()) {
				this.level().addFreshEntity(experienceOrbTitan);
			}
		}
		Map<ItemStack, Integer> drops = new HashMap<ItemStack, Integer>();
		drops.put(new ItemStack(Blocks.COAL_BLOCK), 1024);
		drops.put(new ItemStack(Blocks.IRON_BLOCK), 512);
		drops.put(new ItemStack(Blocks.GOLD_BLOCK), 512);
		drops.put(new ItemStack(Blocks.EMERALD_BLOCK), 256);
		drops.put(new ItemStack(Blocks.DIAMOND_BLOCK), 256);
		drops.put(new ItemStack(Blocks.DRAGON_EGG), 256);
		drops.put(new ItemStack(TheTitansNeoBlocks.HARCADIUM_BLOCK_BLOCK_ITEM.get()), 128);
		drops.put(new ItemStack(TheTitansNeoBlocks.VOID_BLOCK_BLOCK_ITEM.get()), 128);
		drops.put(new ItemStack(TheTitansNeoBlocks.BEDROCK_COMPACT_BLOCK_ITEM.get()), 128);
		drops.put(new ItemStack(TheTitansNeoItems.ULTIMA_BLADE.get()), 1);

		for (int i = 0; i < 256;) {
			Item item = BuiltInRegistries.ITEM.byId(this.getRandom().nextInt(BuiltInRegistries.ITEM.size()));
			if (item != null && !(item instanceof ItemUltimaBlade) && !(item instanceof ItemOptimaAxe) && !(item instanceof ItemTitanSpawnEgg)) {
				i++;
				drops.put(new ItemStack(item), 1);
			}
		}
		for (int i = 0; i < 256;) {
			Block block = BuiltInRegistries.BLOCK.byId(this.getRandom().nextInt(BuiltInRegistries.BLOCK.size()));
			if (block != null) {
				i++;
				drops.put(new ItemStack(block), 1);
			}
		}
		for (Map.Entry<ItemStack, Integer> entry : drops.entrySet()) {
			ItemStack itemStack = entry.getKey();
			int count = entry.getValue();
			if (count > 1) {
				EntityItemTitan itemTitan = new EntityItemTitan(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), itemStack, count);
				itemTitan.setPickUpDelay(40);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(itemTitan);
				}
			} else {
				ItemEntity itemEntity = new ItemEntity(this.level(), this.getX() + (this.getRandom().nextFloat() * 12.0F - 6.0F), this.getY() + 4.0D, this.getZ() + (this.getRandom().nextFloat() * 12.0F - 6.0F), itemStack);
				itemEntity.setPickUpDelay(40);
				if (!this.level().isClientSide()) {
					this.level().addFreshEntity(itemEntity);
				}
			}
		}
	}

	@Override
	protected void tickTitanDeath() {
		super.tickTitanDeath();

		if (!this.isInOmegaForm()) {
			this.setPos(0.0D, 120.0D, 0.0D);
			this.setYRot(this.deathTicks * 10);
			this.yHeadRot = this.getYRot();
			this.yBodyRot = this.getYRot();
		}
		if (this.deathTicks == 1 && !this.level().players().isEmpty()) {
			for (Player player : this.level().players()) {
				if (!this.level().isClientSide()) {
					if (this.isInOmegaForm()) {
						player.sendSystemMessage(Component.translatable("entity.thetitansneo.witherzilla.defeat"));
					} else {
						player.sendSystemMessage(Component.translatable("entity.thetitansneo.witherzilla.death"));
					}
				}
			}
		}
		if (this.deathTicks > 180 && this.deathTicks % 1 == 0) {
			float f = (this.getRandom().nextFloat() - 0.5F) * 24.0F;
			float f2 = (this.getRandom().nextFloat() - 0.5F) * 80.0F;
			float f3 = (this.getRandom().nextFloat() - 0.5F) * 24.0F;
			this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + f, this.getY() + 2.0 + f2, this.getZ() + f3, 0.0D, 0.0D, 0.0D);
		}
		if (this.deathTicks >= 200) {
			this.setInvulTime(2000);
		}
		if (this.deathTicks >= 400) {
			this.onDeath();
			this.createBeaconPortal(Mth.floor(this.getX()), Mth.floor(this.getZ()));

			if (!this.level().isClientSide() && !this.isRemoved()) {
				this.removeTitan(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.refreshDimensions();
		this.refreshAttributes();

		if (this.isInOmegaForm()) {
			if (!this.level().isClientSide()) {
				ServerLevel serverLevel = (ServerLevel) this.level();

				if (this.getTarget() != null && this.getTarget() instanceof EntityEnderColossus) {
					serverLevel.setWeatherParameters(0, 0, false, false);
				} else {
					serverLevel.setWeatherParameters(0, 1000000, true, true);
				}
			}
			if (this.getRandom().nextInt(2) == 0) {
				for (int l = 0; l < 20; l++) {
					int m = Mth.floor(this.getX());
					int n = Mth.floor(this.getY());
					int k = Mth.floor(this.getZ());
					int i1 = m + Mth.randomBetweenInclusive(this.getRandom(), 10, 100) * Mth.randomBetweenInclusive(this.getRandom(), -1, 1);
					int j1 = n + Mth.randomBetweenInclusive(this.getRandom(), 10, 100) * Mth.randomBetweenInclusive(this.getRandom(), -1, 1);
					int k2 = k + Mth.randomBetweenInclusive(this.getRandom(), 10, 100) * Mth.randomBetweenInclusive(this.getRandom(), -1, 1);
					EntityColorLightningBolt colorLightningBolt = new EntityColorLightningBolt(this.level(), this.getRandom().nextFloat(), this.getRandom().nextFloat(), this.getRandom().nextFloat());
					colorLightningBolt.setPos(i1, j1, k2);
					if (this.getRandom().nextInt(5) == 0 && !this.level().isClientSide()) {
						this.level().addFreshEntity(colorLightningBolt);
					}
				}
			}
		} else {
			if (this.distanceToSqr(0.0D, 200.0D, 0.0D) > 50000.0D) {
				this.setPos(0.0D, 200.0D, 0.0D);
			}

			List<EntityWitherTurret> entities = this.level().getEntitiesOfClass(EntityWitherTurret.class, this.getBoundingBox().inflate(20000.0D, 20000.0D, 20000.0D));
			if (!entities.isEmpty()) {
				for (EntityWitherTurret entity : entities) {
					if (entity != null && entity.isAlive()) {
						if (!entity.isPlayerCreated()) {
							this.setPos(0.0D, 200.0D, 0.0D);
							this.setYRot(0.0F);
							this.yHeadRot = 0.0F;
							this.yBodyRot = 0.0F;
							this.setTarget(null);
							for (int i = 0; i < 3; i++) {
								this.setAlternativeTarget(i, 0);
							}
						}
					}
				}
			} else {
				if (this.getY() > 100.0D) {
					this.setPos(this.getX(), this.getY() - 1.0D, this.getZ());
				}
			}
		}

		this.affectTicks++;
		if (this.affectTicks >= 800) {
			this.setInvisible(true);
		} else {
			this.setInvisible(false);
		}
		if (this.affectTicks >= 1010) {
			this.affectTicks = 0;
		}
		this.omegaCounter--;

		if (!this.level().isClientSide()) {
			ServerLevel serverLevel = (ServerLevel) this.level();
			serverLevel.setDayTime(18000L);
		}

		if (this.getTarget() != null && !this.getTarget().isAlive()) {
			this.setTarget(null);
		}

		if (!this.isArmored()) {
			this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y * 0.1D, this.getDeltaMovement().z);
		} else {
			this.setTitanDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y * 0.9D, this.getDeltaMovement().z);
		}

		if (!this.level().players().isEmpty() && this.getRandom().nextInt(4) == 0) {
			for (Player player : this.level().players()) {
				if (this.getRandom().nextInt(100) == 0) {
					this.playAmbientSound();
					if (this.isInOmegaForm()) {
						this.attackEntity(player, 10.0F);
						player.sendSystemMessage(Component.translatable("entity.thetitansneo.witherzilla.taunt." + this.getRandom().nextInt(6)));
					} else {
						player.sendSystemMessage(Component.translatable("entity.thetitansneo.witherzilla.plead." + this.getRandom().nextInt(6)));
					}
				}
			}
		}

		if (this.isInOmegaForm()) {
			List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().inflate(20000.0D, 20000.0D, 20000.0D));
			for (Entity entity : entities) {
				if (entity != null && entity.isAlive() && !(entity instanceof LightningBolt) && !(entity instanceof WitherSkull) && !(entity instanceof EntityWitherTurret) && !(entity instanceof EntityTitan) && !(entity instanceof Player)) {
					if (this.getInvulTime() > 1) {
						if (entity instanceof LivingEntity livingEntity) {
							if (livingEntity instanceof Mob) {
								Mob mob = (Mob) livingEntity;
								mob.getNavigation().stop();
							}
							livingEntity.push(0.0D, 0.05D - this.getDeltaMovement().y * 0.2D, 0.0D);
							livingEntity.xxa = (float) this.getRandom().nextGaussian();
							livingEntity.zza = (float) this.getRandom().nextGaussian();
							livingEntity.setYRot(livingEntity.getYRot() + (float) this.getRandom().nextGaussian() * 10.0F);
							livingEntity.yHeadRot = livingEntity.getYRot();
							livingEntity.yBodyRot = livingEntity.getYRot();
							livingEntity.invulnerableTime = (int) this.getRandom().nextGaussian() * 20;
						} else {
							entity.push(this.getRandom().nextGaussian() * 0.5D, this.getRandom().nextGaussian() * 0.5D, this.getRandom().nextGaussian() * 0.5D);
							entity.setYRot(entity.getYRot() + 10.0F);
							entity.invulnerableTime = (int) this.getRandom().nextGaussian() * 20;
						}
					} else {
						this.doLightningAttackTo(entity);
					}
				}
			}
		}
		if (this.getTarget() != null && (this.affectTicks >= 600 || (this.getTarget().getBbHeight() > 6.0f && !(this.getTarget() instanceof EntityTitan)))) {
			if (this.getRandom().nextInt(120) == 0) {
				if (!this.level().isClientSide()) {
					this.level().explode(this, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 14.0F, true, Level.ExplosionInteraction.MOB);
				}
			}
			this.doLightningAttackTo(this.getTarget());
			for (int i = 0; i < 19; i++) {
				this.doLightningAttackTo(this.getTarget());
				this.getTarget().hurt(this.damageSources().fellOutOfWorld(), 1.0F);
			}
		}

		if (this.getAlternativeTarget(0) > 0) {
			Entity entity = this.level().getEntity(this.getAlternativeTarget(0));
			if (entity != null) {
				double d0 = entity.getX() - this.getX();
				double d1 = entity.getY() + (this.isArmored() ? 2.0D : 12.0D) - getY();
				double d2 = entity.getZ() - this.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				this.getLookControl().setLookAt(entity, 180.0F, 40.0F);

				if (entity instanceof LivingEntity && d3 < 10000.0D) {
					LivingEntity livingEntity = (LivingEntity) entity;
					this.performRangedAttack(livingEntity, 0.0F);
				}
				if (d3 > 64.0D) {
					double d4 = Math.sqrt(d3);
					this.setTitanDeltaMovement(this.getDeltaMovement().add(d0 / d4 * 1.5D - this.getDeltaMovement().x, d1 / d4 * 1.5D - this.getDeltaMovement().y, d2 / d4 * 1.5D - this.getDeltaMovement().z));
					this.setYRot(((float) Math.atan2(this.getDeltaMovement().z, this.getDeltaMovement().x) * 180.0F / Mth.PI) - 90.0F);
					this.yBodyRot = this.getYRot();
				}
			}
		}

		for (int i = 0; i < 2; i++) {
			this.yRotOHeads[i] = this.yRotHeads[i];
			this.xRotOHeads[i] = this.xRotHeads[i];
		}
		for (int j = 0; j < 2; j++) {
			int k = this.getAlternativeTarget(j + 1);
			Entity entity = null;
			if (k > 0) {
				entity = this.level().getEntity(k);
			}
			if (entity != null) {
				double d6 = this.getHeadX(j + 1);
				double d7 = this.getHeadY(j + 1);
				double d4 = this.getHeadZ(j + 1);
				double d8 = entity.getX() - d6;
				double d9 = entity.getEyeY() - d7;
				double d10 = entity.getZ() - d4;
				double d11 = Math.sqrt(d8 * d8 + d10 * d10);
				float f1 = (float) (Mth.atan2(d10, d8) * 180.0F / Mth.PI) - 90.0F;
				float f2 = (float) (-(Mth.atan2(d9, d11) * 180.0F / Mth.PI));
				this.xRotHeads[j] = this.rotlerp(this.xRotHeads[j], f2, 5.0F);
				this.yRotHeads[j] = this.rotlerp(this.yRotHeads[j], f1, 5.0F);
			} else {
				if (this.getRandom().nextInt(120) == 0) {
					for (k = 0; k < 36; k++) {
						this.xRotHeads[j] = this.rotlerp(this.xRotHeads[j], this.getRandom().nextFloat() * 360.0F - 180.0F, 5.0F);
					}
				}
				if (this.getRandom().nextInt(120) == 0) {
					for (k = 0; k < 36; k++) {
						this.yRotHeads[j] = this.rotlerp(this.yRotHeads[j], this.getRandom().nextFloat() * 360.0F - 180.0F, 5.0F);
					}
				}
			}
		}

		boolean flag = this.isArmored();

		for (int l = 0; l < 100; l++) {
			double d12 = this.getHeadX(l);
			double d13 = this.getHeadY(l);
			double d14 = this.getHeadZ(l);
			float f = 0.3F * this.getScale();

			this.level().addParticle(ParticleTypes.SMOKE, d12 + this.random.nextGaussian() * f, d13 + this.random.nextGaussian() * f, d14 + this.random.nextGaussian() * f, 0.0D, 0.0D, 0.0D);

			if (flag && this.level().random.nextInt(4) == 0) {
				this.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0.7F, 0.7F, 0.5F), d12 + this.random.nextGaussian() * f, d13 + this.random.nextGaussian() * f, d14 + this.random.nextGaussian() * f, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
