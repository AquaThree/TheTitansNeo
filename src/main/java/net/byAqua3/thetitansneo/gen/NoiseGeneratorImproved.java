package net.byAqua3.thetitansneo.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {

	private final int[] permutationTable;
	public double xOffset;
	public double yOffset;
	public double zOffset;
	private static final double[] GRADIENT_X = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
	private static final double[] GRADIENT_Y = new double[] { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D };
	private static final double[] GRADIENT_Z = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
	private static final double[] GRADIENT_2D_X = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
	private static final double[] GRADIENT_2D_Z = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };

	public NoiseGeneratorImproved() {
		this(new Random());
	}

	public NoiseGeneratorImproved(Random random) {
		this.permutationTable = new int[512];
		this.xOffset = random.nextDouble() * 256.0D;
		this.yOffset = random.nextDouble() * 256.0D;
		this.zOffset = random.nextDouble() * 256.0D;

		for (int i = 0; i < 256; i++) {
			this.permutationTable[i] = i;
		}

		for (int i = 0; i < 256; i++) {
			int randomIndex = random.nextInt(256 - i) + i;
			int temp = this.permutationTable[i];
			this.permutationTable[i] = this.permutationTable[randomIndex];
			this.permutationTable[randomIndex] = temp;
			this.permutationTable[i + 256] = this.permutationTable[i];
		}
	}

	public final double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}

	public final double calculateGradient2D(int hash, double x, double z) {
		int index = hash & 15;
		return GRADIENT_2D_X[index] * x + GRADIENT_2D_Z[index] * z;
	}

	public final double calculateGradient3D(int hash, double x, double y, double z) {
		int index = hash & 15;
		return GRADIENT_X[index] * x + GRADIENT_Y[index] * y + GRADIENT_Z[index] * z;
	}

	public void populateNoiseArray(double[] noiseArray, double x, double y, double z, int width, int height, int depth, double xScale, double yScale, double zScale, double noiseScale) {
		if (height == 1) {
			int tempIndex = 0;
			int hashA = 0;
			int hashB = 0;
			int hashC = 0;
			int hashD = 0;
			double noiseValueA = 0.0D;
			double noiseValueB = 0.0D;
			double inverseNoiseScale = 1.0D / noiseScale;

			for (int xIndex = 0; xIndex < width; xIndex++) {
				double sampleX = x + (double) xIndex * xScale + this.xOffset;
				int xFloor = (int) sampleX;

				if (sampleX < (double) xFloor) {
					xFloor--;
				}

				int xWrapped = xFloor & 255;
				double xFrac = sampleX - (double) xFloor;
				double xFade = xFrac * xFrac * xFrac * (xFrac * (xFrac * 6.0D - 15.0D) + 10.0D);

				for (int zIndex = 0; zIndex < depth; zIndex++) {
					double sampleZ = z + (double) zIndex * zScale + this.zOffset;
					int zFloor = (int) sampleZ;

					if (sampleZ < (double) zFloor) {
						zFloor--;
					}

					int zWrapped = zFloor & 255;
					double zFrac = sampleZ - (double) zFloor;
					double zFade = zFrac * zFrac * zFrac * (zFrac * (zFrac * 6.0D - 15.0D) + 10.0D);

					hashA = this.permutationTable[xWrapped] + 0;
					hashB = this.permutationTable[hashA] + zWrapped;
					hashC = this.permutationTable[xWrapped + 1] + 0;
					hashD = this.permutationTable[hashC] + zWrapped;

					noiseValueA = this.lerp(xFade, this.calculateGradient2D(this.permutationTable[hashB], xFrac, zFrac), this.calculateGradient3D(this.permutationTable[hashD], xFrac - 1.0D, 0.0D, zFrac));

					noiseValueB = this.lerp(xFade, this.calculateGradient3D(this.permutationTable[hashB + 1], xFrac, 0.0D, zFrac - 1.0D), this.calculateGradient3D(this.permutationTable[hashD + 1], xFrac - 1.0D, 0.0D, zFrac - 1.0D));

					double noiseValue = this.lerp(zFade, noiseValueA, noiseValueB);
					int currentIndex = tempIndex++;
					noiseArray[currentIndex] += noiseValue * inverseNoiseScale;
				}
			}
		} else {
			int currentIndex = 0;
			double inverseNoiseScale = 1.0D / noiseScale;
			int previousYHash = -1;
			int hashAA = 0;
			int hashAB = 0;
			int hashBA = 0;
			int hashBB = 0;
			int hashCA = 0;
			int hashCB = 0;
			double noiseValueAA = 0.0D;
			double noiseValueAB = 0.0D;
			double noiseValueBA = 0.0D;
			double noiseValueBB = 0.0D;

			for (int xIndex = 0; xIndex < width; xIndex++) {
				double sampleX = x + (double) xIndex * xScale + this.xOffset;
				int xFloor = (int) sampleX;

				if (sampleX < (double) xFloor) {
					xFloor--;
				}

				int xWrapped = xFloor & 255;
				double xFrac = sampleX - (double) xFloor;
				double xFade = xFrac * xFrac * xFrac * (xFrac * (xFrac * 6.0D - 15.0D) + 10.0D);

				for (int zIndex = 0; zIndex < depth; zIndex++) {
					double sampleZ = z + (double) zIndex * zScale + this.zOffset;
					int zFloor = (int) sampleZ;

					if (sampleZ < (double) zFloor) {
						zFloor--;
					}

					int zWrapped = zFloor & 255;
					double zFrac = sampleZ - (double) zFloor;
					double zFade = zFrac * zFrac * zFrac * (zFrac * (zFrac * 6.0D - 15.0D) + 10.0D);

					for (int yIndex = 0; yIndex < height; yIndex++) {
						double sampleY = y + (double) yIndex * yScale + this.yOffset;
						int yFloor = (int) sampleY;

						if (sampleY < (double) yFloor) {
							yFloor--;
						}

						int yWrapped = yFloor & 255;
						double yFrac = sampleY - (double) yFloor;
						double yFade = yFrac * yFrac * yFrac * (yFrac * (yFrac * 6.0D - 15.0D) + 10.0D);

						if (yIndex == 0 || yWrapped != previousYHash) {
							previousYHash = yWrapped;
							hashAA = this.permutationTable[xWrapped] + yWrapped;
							hashAB = this.permutationTable[hashAA] + zWrapped;
							hashBA = this.permutationTable[hashAA + 1] + zWrapped;
							hashBB = this.permutationTable[xWrapped + 1] + yWrapped;
							hashCA = this.permutationTable[hashBB] + zWrapped;
							hashCB = this.permutationTable[hashBB + 1] + zWrapped;
							noiseValueAA = this.lerp(xFade, this.calculateGradient3D(this.permutationTable[hashAB], xFrac, yFrac, zFrac), this.calculateGradient3D(this.permutationTable[hashCA], xFrac - 1.0D, yFrac, zFrac));
							noiseValueAB = this.lerp(xFade, this.calculateGradient3D(this.permutationTable[hashBA], xFrac, yFrac - 1.0D, zFrac), this.calculateGradient3D(this.permutationTable[hashCB], xFrac - 1.0D, yFrac - 1.0D, zFrac));
							noiseValueBA = this.lerp(xFade, this.calculateGradient3D(this.permutationTable[hashAB + 1], xFrac, yFrac, zFrac - 1.0D), this.calculateGradient3D(this.permutationTable[hashCA + 1], xFrac - 1.0D, yFrac, zFrac - 1.0D));
							noiseValueBB = this.lerp(xFade, this.calculateGradient3D(this.permutationTable[hashBA + 1], xFrac, yFrac - 1.0D, zFrac - 1.0D), this.calculateGradient3D(this.permutationTable[hashCB + 1], xFrac - 1.0D, yFrac - 1.0D, zFrac - 1.0D));
						}

						double noiseValueY1 = this.lerp(yFade, noiseValueAA, noiseValueAB);
						double noiseValueY2 = this.lerp(yFade, noiseValueBA, noiseValueBB);
						double noiseValue = this.lerp(zFade, noiseValueY1, noiseValueY2);
						noiseArray[currentIndex++] += noiseValue * inverseNoiseScale;
					}
				}
			}
		}
	}
}