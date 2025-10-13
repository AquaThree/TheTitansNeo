package net.byAqua3.thetitansneo.gen;

import java.util.Random;
import net.minecraft.util.Mth;

public class NoiseGeneratorOctaves extends NoiseGenerator {

	private final NoiseGeneratorImproved[] noiseGenerators;
	private final int octaveCount;

	public NoiseGeneratorOctaves(Random random, int octaveCount) {
		this.octaveCount = octaveCount;
		this.noiseGenerators = new NoiseGeneratorImproved[octaveCount];

		for (int i = 0; i < octaveCount; ++i) {
			this.noiseGenerators[i] = new NoiseGeneratorImproved(random);
		}
	}

	public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale) {
		if (noiseArray == null) {
			noiseArray = new double[xSize * ySize * zSize];
		} else {
			for (int i = 0; i < noiseArray.length; ++i) {
				noiseArray[i] = 0.0D;
			}
		}

		double amplitude = 1.0D;

		for (int octave = 0; octave < this.octaveCount; ++octave) {
			double sampleX = (double) xOffset * amplitude * xScale;
			double sampleY = (double) yOffset * amplitude * yScale;
			double sampleZ = (double) zOffset * amplitude * zScale;

			long floorX = Mth.lfloor(sampleX);
			long floorZ = Mth.lfloor(sampleZ);

			sampleX = sampleX - (double) floorX;
			sampleZ = sampleZ - (double) floorZ;

			floorX = floorX % 16777216L;
			floorZ = floorZ % 16777216L;

			sampleX = sampleX + (double) floorX;
			sampleZ = sampleZ + (double) floorZ;

			this.noiseGenerators[octave].populateNoiseArray(noiseArray, sampleX, sampleY, sampleZ, xSize, ySize, zSize, xScale * amplitude, yScale * amplitude, zScale * amplitude, amplitude);

			amplitude /= 2.0D;
		}

		return noiseArray;
	}

	public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int zOffset, int xSize, int zSize, double xScale, double zScale, double amplitude) {
		return this.generateNoiseOctaves(noiseArray, xOffset, 10, zOffset, xSize, 1, zSize, xScale, 1.0D, zScale);
	}
}