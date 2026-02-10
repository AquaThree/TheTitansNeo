package net.byAqua3.thetitansneo.gen;

public interface IChunkGeneratorGenStructures {
	
	public static enum GenStructuresBoolean {
		DEFAULT, TRUE, FALSE;
	}

	public default GenStructuresBoolean shouldGenerateStructures() {
		return GenStructuresBoolean.DEFAULT;
	}}
