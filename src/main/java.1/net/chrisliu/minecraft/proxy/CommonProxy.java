package net.chrisliu.minecraft.proxy;

import java.util.HashMap;
import java.util.Map;

import net.chrisliu.minecraft.ChrisLiuMod;
import net.chrisliu.minecraft.client.gui.ContainerTeleport;
import net.chrisliu.minecraft.client.gui.GuiTeleport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	/** Used to store IExtendedEntityProperties data temporarily between player death and respawn or dimension change */
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

	public void registerRenderers() {}

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		
		System.out.println("[debug] CommonProxy getServerGuiElement guiId == " + guiId);
		
		if (guiId == ChrisLiuMod.GUI_TELEPORT)  {
			//return new ContainerCustomPlayer(player, player.inventory, ExtendedPlayer.get(player).inventory);
			
			System.out.println("[debug] CommonProxy getServerGuiElement guiId == ChrisLiuMod.GUI_TELEPORT");
			
			return new ContainerTeleport(player);
		} else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		
		System.out.println("[debug] CommonProxy getClientGuiElement guiId == " + guiId);
		
		if (guiId == ChrisLiuMod.GUI_TELEPORT) {
			//return new GuiCustomPlayerInventory(player, player.inventory, ExtendedPlayer.get(player).inventory);
			
			System.out.println("[debug] CommonProxy getClientGuiElement guiId == ChrisLiuMod.GUI_TELEPORT");
			
			return new GuiTeleport(player);
		} else {
			return null;
		}
	}

	/**
	 * Adds an entity's custom data to the map for temporary storage
	 */
	public static void storeEntityData(String name, NBTTagCompound compound) {
		extendedEntityData.put(name, compound);
	}

	/**
	 * Removes the compound from the map and returns the NBT tag stored for name or null if none exists
	 */
	public static NBTTagCompound getEntityData(String name) {
		return extendedEntityData.remove(name);
	}
}
