package net.byAqua3.thetitansneo.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.mojang.math.Transformation;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.SimpleModelState;
import net.neoforged.neoforge.client.model.geometry.UnbakedGeometryHelper;

public class RenderUtils {

	public static List<BakedQuad> bakeItem(Transformation state, TextureAtlasSprite... sprites) {
		List<BakedQuad> quads = new LinkedList<>();

		for (int i = 0; i < sprites.length; ++i) {
			TextureAtlasSprite sprite = sprites[i];
			List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(i, sprite);

			for (BlockElement element : unbaked) {
				for (Entry<Direction, BlockElementFace> directionBlockElementFaceEntry : element.faces.entrySet()) {
					quads.add(BlockModel.bakeFace(element, directionBlockElementFaceEntry.getValue(), sprite, directionBlockElementFaceEntry.getKey(), new SimpleModelState(state)));
				}
			}
		}
		return quads;
	}

	public static List<BakedQuad> bakeItem(TextureAtlasSprite... sprites) {
		return bakeItem(Transformation.identity(), sprites);
	}

	public static List<BakedQuad> bakeItem(TextureAtlasSprite sprite) {
		return bakeItem(new TextureAtlasSprite[] { sprite });
	}

}
