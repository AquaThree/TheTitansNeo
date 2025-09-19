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
	public NoiseGeneratorOctaves noiseGen1;
	public NoiseGeneratorOctaves noiseGen2;
	public NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public double[] noiseData1;
	public double[] noiseData2;
	public double[] noiseData3;
	public double[] noiseData4;
	public double[] noiseData5;
	public double[] densities;

	public ChunkGeneratorVoid(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
		super(biomeSource, settings);
		this.settings = settings;
		this.voidRNG = new Random(RandomSupport.generateUniqueSeed());
		this.noiseGen1 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.voidRNG, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.voidRNG, 10);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.voidRNG, 16);
	}

	private double[] getHeights(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_) {
		if (p_73187_1_ == null) {
			p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
		}
		double d0 = 684.412D;
		double d1 = 684.412D;
		this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D, 1.121D, 0.5D);
		this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D, 200.0D, 0.5D);
		d0 *= 2.0D;
		this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
		this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		for (int i2 = 0; i2 < p_73187_5_; i2++) {
			for (int j2 = 0; j2 < p_73187_7_; j2++) {
				double d2 = (this.noiseData4[l1] + 256.0D) / 512.0D;
				if (d2 > 1.0D) {
					d2 = 1.0D;
				}
				double d3 = this.noiseData5[l1] / 8000.0D;
				if (d3 < 0.0D) {
					d3 = -d3 * 0.3D;
				}
				d3 = d3 * 3.0D - 2.0D;
				float f = (i2 + p_73187_2_ - 0) / 1.0F;
				float f1 = (j2 + p_73187_4_ - 0) / 1.0F;
				float f2 = 100.0F - Mth.sqrt(f * f + f1 * f1) * 8.0F;
				if (f2 > 80.0F) {
					f2 = 80.0F;
				}
				if (f2 < -100.0F) {
					f2 = -100.0F;
				}
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				d3 /= 8.0D;
				d3 = 0.0D;
				if (d2 < 0.0D) {
					d2 = 0.0D;
				}
				d2 += 0.5D;
				d3 = d3 * p_73187_6_ / 16.0D;
				l1++;
				double d4 = p_73187_6_ / 2.0D;
				for (int k2 = 0; k2 < p_73187_6_; k2++) {
					double d5 = 0.0D;
					double d6 = (k2 - d4) * 8.0D / d2;
					if (d6 < 0.0D) {
						d6 *= -1.0D;
					}
					double d7 = this.noiseData2[k1] / 512.0D;
					double d8 = this.noiseData3[k1] / 512.0D;
					double d9 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;
					if (d9 < 0.0D) {
						d5 = d7;
					} else if (d9 > 1.0D) {
						d5 = d8;
					} else {
						d5 = d7 + (d8 - d7) * d9;
					}
					d5 -= 8.0D;
					d5 += f2;
					byte b0 = 2;
					if (k2 > p_73187_6_ / 2 - b0) {
						double d10 = ((k2 - p_73187_6_ / 2 - b0) / 64.0F);
						if (d10 < 0.0D) {
							d10 = 0.0D;
						}
						if (d10 > 1.0D) {
							d10 = 1.0D;
						}
						d5 = d5 * (1.0D - d10) + -3000.0D * d10;
					}
					b0 = 8;
					if (k2 < b0) {
						double d10 = ((b0 - k2) / (b0 - 1.0F));
						d5 = d5 * (1.0D - d10) + -30.0D * d10;
					}
					p_73187_1_[k1] = d5;
					k1++;
				}
			}
		}
		return p_73187_1_;
	}
	
	public BlockState[] getBaseColumn(int x, int z, RandomState random) {
		BlockState[] blockState = new BlockState[32768];

		byte b0 = 2;
		int k = b0 + 1;
		byte b1 = 33;
		int l = b0 + 1;
		this.densities = this.getHeights(this.densities, x * b0, 0, z * b0, k, b1, l);
		for (int i1 = 0; i1 < b0; i1++) {
			for (int j1 = 0; j1 < b0; j1++) {
				for (int k1 = 0; k1 < 32; k1++) {
					double d0 = 0.25D;
					double d1 = this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 0];
					double d2 = this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 0];
					double d3 = this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 0];
					double d4 = this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 0];
					double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 1] - d1) * d0;
					double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 1] - d2) * d0;
					double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 1] - d3) * d0;
					double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 1] - d4) * d0;
					for (int l1 = 0; l1 < 4; l1++) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 8; i2++) {
							int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | l1 + k1 * 4;
							short short1 = 128;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 8; k2++) {
								Block block = Blocks.AIR;
								if (d15 > 0.0D) {
									block = Blocks.BEDROCK;
								}

								blockState[j2] = block.defaultBlockState();
								j2 += short1;
								d15 += d16;
							}
							d10 += d12;
							d11 += d13;
						}
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
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
		BlockPos.MutableBlockPos blockPos$mutableblockpos = new BlockPos.MutableBlockPos();
		Heightmap heightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
		Heightmap heightmap1 = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
		int height = this.settings.value().noiseSettings().height();

		for (int i = 0; i < height; i++) {
			int j = chunk.getMinBuildHeight() + i;

			for (int k = 0; k < 16; k++) {
				for (int l = 0; l < 16; l++) {
					BlockState blockState = blockStates[k << 11 | l << 7 | i];
					if (blockState != null) {
						chunk.setBlockState(blockPos$mutableblockpos.set(k, j, l), blockState, false);
						heightmap.update(k, j, l, blockState);
						heightmap1.update(k, j, l, blockState);
					}
				}
			}
		}
		return CompletableFuture.completedFuture(chunk);
	}

}
