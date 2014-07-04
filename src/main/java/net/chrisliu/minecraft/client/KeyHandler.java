package net.chrisliu.minecraft.client;

import net.chrisliu.minecraft.ChrisLiuMod;
import net.chrisliu.minecraft.network.packet.OpenGuiPacket;
import net.chrisliu.minecraft.network.packet.TeleportPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler
{
	/** Storing an instance of Minecraft in a local variable saves having to get it every time */
	private final Minecraft mc;
	
	/** Key index for easy handling */
	public static final int KEY_TELEPORT_HOME = 0, KEY_TELEPORT = 1;

	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = {"net.chrisliu.minecraft.teleport.home", "net.chrisliu.minecraft.teleport"};

	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_HOME, Keyboard.KEY_END};

	/** Make this public or provide a getter if you'll need access to the key bindings from elsewhere */
	public static final KeyBinding[] keys = new KeyBinding[desc.length];

	public KeyHandler() {
		mc = Minecraft.getMinecraft();
		/*
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], StatCollector.translateToLocal("net.chrisliu.minecraft.category"));
			ClientRegistry.registerKeyBinding(keys[i]);
		}
		*/
		
		keys[0] = new KeyBinding(desc[0], keyValues[0], StatCollector.translateToLocal("net.chrisliu.minecraft.category"));
		ClientRegistry.registerKeyBinding(keys[0]);
		if (ChrisLiuMod.ENABLE_PLAYER_TELEPORT) {
			keys[1] = new KeyBinding(desc[1], keyValues[1], StatCollector.translateToLocal("net.chrisliu.minecraft.category"));
			ClientRegistry.registerKeyBinding(keys[1]);
		}
		
	}

	/**
	 * KeyInputEvent is in the FML package, so we must register to the FML event bus
	 */
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		// checking inGameHasFocus prevents your keys from firing when the player is typing a chat message
		// NOTE that the KeyInputEvent will NOT be posted when a gui screen such as the inventory is open
		// so we cannot close an inventory screen from here; that should be done in the GUI itself
		if (mc.inGameHasFocus) {
			if (ChrisLiuMod.ENABLE_PLAYER_TELEPORT) {
				if (keys[KEY_TELEPORT].getIsKeyPressed()) {
					ChrisLiuMod.packetPipeline.sendToServer(new OpenGuiPacket(ChrisLiuMod.GUI_TELEPORT));
					return;
				}
			}
			
			if (keys[KEY_TELEPORT_HOME].getIsKeyPressed()) {
				ChrisLiuMod.packetPipeline.sendToServer(new TeleportPacket("home"));
				return;
			}
		}
	}
}
