package net.byAqua3.thetitansneo.gen;

public interface IChunkGeneratorGenStructures {

	public default GenStructuresBoolean shouldGenerateStructures() {
		return GenStructuresBoolean.DEFAULT;
	}

	public static enum GenStructuresBoolean {
		DEFAULT, TRUE, FALSE;
	}

}
