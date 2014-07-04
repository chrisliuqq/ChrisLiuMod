package net.chrisliu.minecraft.proxy;

import net.chrisliu.minecraft.client.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers() {
				
		// KeyInputEvent is in the FML package, meaning it's posted to the FML event bus
		// rather than the regular Forge event bus:
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
}
