package net.byAqua3.thetitansneo.gen;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.blending.Blender;

public class ChunkGeneratorVoid extends NoiseBasedChunkGenerator implements IChunkGeneratorGenStructures {

	public static final MapCodec<ChunkGeneratorVoid> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.getBiomeSource()), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> generator.settings)).apply(instance, instance.stable(ChunkGeneratorVoid::new)));

	private final Holder<NoiseGeneratorSettings> settings;

	public Random voidRNG;
	public NoiseGeneratorOctaves surfaceNoiseGenerator;
	public NoiseGeneratorOctaves depthNoiseGenerator;
	public NoiseGeneratorOctaves mainNoiseGenerator;
	public NoiseGeneratorOctaves scaleNoiseGenerator;
	public NoiseGeneratorOctaves biomeDepthNoiseGenerator;
	public double[] surfaceNoiseBuffer;
	public double[] depthNoiseBuffer;
	public double[] mainNoiseBuffer;
	public double[] scaleNoiseBuffer;
	public double[] biomeDepthNoiseBuffer;
	public double[] densities;

	public ChunkGeneratorVoid(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
		super(biomeSource, settings);
		this.settings = settings;
		this.voidRNG = new Random();
		this.setSeed(RandomSupport.generateUniqueSeed());
	}

	private void initNoiseGenerators() {
		this.surfaceNoiseGenerator = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.depthNoiseGenerator = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.mainNoiseGenerator = new NoiseGeneratorOctaves(this.voidRNG, 8);
		this.scaleNoiseGenerator = new NoiseGeneratorOctaves(this.voidRNG, 10);
		this.biomeDepthNoiseGenerator = new NoiseGeneratorOctaves(this.voidRNG, 16);
	}

	public void setSeed(long seed) {
		this.voidRNG.setSeed(seed);
		this.initNoiseGenerators();
	}

	private double[] getHeights(double[] heightBuffer, int chunkX, int minY, int chunkZ, int sizeX, int sizeY, int sizeZ) {
		if (heightBuffer == null) {
			heightBuffer = new double[sizeX * sizeY * sizeZ];
		}

		double noiseScaleX = 684.412D;
		double noiseScaleZ = 684.412D;

		this.scaleNoiseBuffer = this.scaleNoiseGenerator.generateNoiseOctaves(this.scaleNoiseBuffer, chunkX, chunkZ, sizeX, sizeZ, 1.121D, 1.121D, 0.5D);

		this.biomeDepthNoiseBuffer = this.biomeDepthNoiseGenerator.generateNoiseOctaves(this.biomeDepthNoiseBuffer, chunkX, chunkZ, sizeX, sizeZ, 200.0D, 200.0D, 0.5D);

		noiseScaleX *= 2.0D;

		this.mainNoiseBuffer = this.mainNoiseGenerator.generateNoiseOctaves(this.mainNoiseBuffer, chunkX, minY, chunkZ, sizeX, sizeY, sizeZ, noiseScaleX / 80.0D, noiseScaleZ / 160.0D, noiseScaleX / 80.0D);

		this.surfaceNoiseBuffer = this.surfaceNoiseGenerator.generateNoiseOctaves(this.surfaceNoiseBuffer, chunkX, minY, chunkZ, sizeX, sizeY, sizeZ, noiseScaleX, noiseScaleZ, noiseScaleX);

		this.depthNoiseBuffer = this.depthNoiseGenerator.generateNoiseOctaves(this.depthNoiseBuffer, chunkX, minY, chunkZ, sizeX, sizeY, sizeZ, noiseScaleX, noiseScaleZ, noiseScaleX);

		int bufferIndex = 0;
		int noiseIndex = 0;

		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				double scaleFactor = (this.scaleNoiseBuffer[noiseIndex] + 256.0D) / 512.0D;
				if (scaleFactor > 1.0D) {
					scaleFactor = 1.0D;
				}

				double depthVariation = this.biomeDepthNoiseBuffer[noiseIndex] / 8000.0D;
				if (depthVariation < 0.0D) {
					depthVariation = -depthVariation * 0.3D;
				}

				depthVariation = depthVariation * 3.0D - 2.0D;

				float distanceX = (x + chunkX - 0) / 1.0F;
				float distanceZ = (z + chunkZ - 0) / 1.0F;
				float distanceFactor = 100.0F - Mth.sqrt(distanceX * distanceX + distanceZ * distanceZ) * 8.0F;

				if (distanceFactor > 80.0F) {
					distanceFactor = 80.0F;
				}
				if (distanceFactor < -100.0F) {
					distanceFactor = -100.0F;
				}

				if (depthVariation > 1.0D) {
					depthVariation = 1.0D;
				}

				depthVariation /= 8.0D;
				depthVariation = 0.0D;

				if (scaleFactor < 0.0D) {
					scaleFactor = 0.0D;
				}

				scaleFactor += 0.5D;
				depthVariation = depthVariation * sizeY / 16.0D;
				noiseIndex++;

				double centerY = sizeY / 2.0D;

				for (int y = 0; y < sizeY; y++) {
					double heightValue = 0.0D;
					double verticalScale = (y - centerY) * 8.0D / scaleFactor;

					if (verticalScale < 0.0D) {
						verticalScale *= -1.0D;
					}

					double surfaceNoise = this.surfaceNoiseBuffer[bufferIndex] / 512.0D;
					double depthNoise = this.depthNoiseBuffer[bufferIndex] / 512.0D;
					double noiseBlend = (this.mainNoiseBuffer[bufferIndex] / 10.0D + 1.0D) / 2.0D;

					if (noiseBlend < 0.0D) {
						heightValue = surfaceNoise;
					} else if (noiseBlend > 1.0D) {
						heightValue = depthNoise;
					} else {
						heightValue = surfaceNoise + (depthNoise - surfaceNoise) * noiseBlend;
					}

					heightValue -= 8.0D;
					heightValue += distanceFactor;

					int topTransitionRange = 2;
					if (y > sizeY / 2 - topTransitionRange) {
						double topTransition = ((y - sizeY / 2 - topTransitionRange) / 64.0F);
						if (topTransition < 0.0D) {
							topTransition = 0.0D;
						}
						if (topTransition > 1.0D) {
							topTransition = 1.0D;
						}
						heightValue = heightValue * (1.0D - topTransition) + -3000.0D * topTransition;
					}

					int bottomTransitionRange = 8;
					if (y < bottomTransitionRange) {
						double bottomTransition = ((bottomTransitionRange - y) / (bottomTransitionRange - 1.0F));
						heightValue = heightValue * (1.0D - bottomTransition) + -30.0D * bottomTransition;
					}

					heightBuffer[bufferIndex] = heightValue;
					bufferIndex++;
				}
			}
		}

		return heightBuffer;
	}

	public BlockState[] getBaseColumn(int x, int z, RandomState random) {
		BlockState[] blockState = new BlockState[32768];

		byte samplingFactor = 2;
		int sampleSizeX = samplingFactor + 1;
		byte sampleSizeY = 33;
		int sampleSizeZ = samplingFactor + 1;
		this.densities = this.getHeights(this.densities, x * samplingFactor, 0, z * samplingFactor, sampleSizeX, sampleSizeY, sampleSizeZ);
		for (int xSample = 0; xSample < samplingFactor; xSample++) {
			for (int zSample = 0; zSample < samplingFactor; zSample++) {
				for (int ySample = 0; ySample < 32; ySample++) {
					double interpolationStep = 0.25D;
					double density000 = this.densities[((xSample + 0) * sampleSizeZ + zSample + 0) * sampleSizeY + ySample + 0];
					double density001 = this.densities[((xSample + 0) * sampleSizeZ + zSample + 1) * sampleSizeY + ySample + 0];
					double density100 = this.densities[((xSample + 1) * sampleSizeZ + zSample + 0) * sampleSizeY + ySample + 0];
					double density101 = this.densities[((xSample + 1) * sampleSizeZ + zSample + 1) * sampleSizeY + ySample + 0];
					double densityDelta000 = (this.densities[((xSample + 0) * sampleSizeZ + zSample + 0) * sampleSizeY + ySample + 1] - density000) * interpolationStep;
					double densityDelta001 = (this.densities[((xSample + 0) * sampleSizeZ + zSample + 1) * sampleSizeY + ySample + 1] - density001) * interpolationStep;
					double densityDelta100 = (this.densities[((xSample + 1) * sampleSizeZ + zSample + 0) * sampleSizeY + ySample + 1] - density100) * interpolationStep;
					double densityDelta101 = (this.densities[((xSample + 1) * sampleSizeZ + zSample + 1) * sampleSizeY + ySample + 1] - density101) * interpolationStep;
					for (int ySubSample = 0; ySubSample < 4; ySubSample++) {
						double xInterpolationStep = 0.25D;
						double currentDensity000 = density000;
						double currentDensity001 = density001;
						double xDensityDelta000 = (density100 - density000) * xInterpolationStep;
						double xDensityDelta001 = (density101 - density001) * xInterpolationStep;
						for (int xSubSample = 0; xSubSample < 8; xSubSample++) {
							int blockIndex = xSubSample + xSample * 8 << 11 | 0 + zSample * 8 << 7 | ySubSample + ySample * 4;
							short blockIncrement = 128;
							double zInterpolationStep = 0.25D;
							double currentDensity = currentDensity000;
							double zDensityDelta = (currentDensity001 - currentDensity000) * zInterpolationStep;
							for (int zSubSample = 0; zSubSample < 8; zSubSample++) {
								Block block = Blocks.AIR;
								if (currentDensity > 0.0D) {
									block = Blocks.BEDROCK;
								}

								blockState[blockIndex] = block.defaultBlockState();
								blockIndex += blockIncrement;
								currentDensity += zDensityDelta;
							}
							currentDensity000 += xDensityDelta000;
							currentDensity001 += xDensityDelta001;
						}
						density000 += densityDelta000;
						density001 += densityDelta001;
						density100 += densityDelta100;
						density101 += densityDelta101;
					}
				}
			}
		}
		return blockState;
	}

	@Override
	public GenStructuresBoolean shouldGenerateStructures() {
		return GenStructuresBoolean.TRUE;
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
		BlockState[] blockStates = this.getBaseColumn(chunk.getPos().x, chunk.getPos().z, randomState);
		BlockPos.MutableBlockPos blockPos$mutableBlockPos = new BlockPos.MutableBlockPos();
		Heightmap heightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
		Heightmap heightmap1 = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
		int height = this.settings.value().noiseSettings().height();

		for (int i = 0; i < height; i++) {
			int j = chunk.getMinBuildHeight() + i;

			for (int k = 0; k < 16; k++) {
				for (int l = 0; l < 16; l++) {
					BlockState blockState = blockStates[k << 11 | l << 7 | i];
					if (blockState != null) {
						chunk.setBlockState(blockPos$mutableBlockPos.set(k, j, l), blockState, false);
						heightmap.update(k, j, l, blockState);
						heightmap1.update(k, j, l, blockState);
					}
				}
			}
		}
		return CompletableFuture.completedFuture(chunk);
	}}