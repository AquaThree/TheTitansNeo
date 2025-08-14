package net.byAqua3.thetitansneo.loader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class TheTitansNeoRenderTypes {

	public static final RenderType WITHERZILLA_RAYS = RenderType.create("witherzilla_rays", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, 1536, false, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_SHADER).setWriteMaskState(RenderType.COLOR_WRITE).setTransparencyState(RenderType.LIGHTNING_TRANSPARENCY).setOutputState(RenderType.WEATHER_TARGET).createCompositeState(false));
	public static final RenderType WITHERZILLA_RAYS_DEPTH = RenderType.create("witherzilla_rays_depth", DefaultVertexFormat.POSITION, VertexFormat.Mode.TRIANGLES, 1536, false, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_SHADER).setWriteMaskState(RenderType.DEPTH_WRITE).setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).setOutputState(RenderType.WEATHER_TARGET).createCompositeState(false));

}
