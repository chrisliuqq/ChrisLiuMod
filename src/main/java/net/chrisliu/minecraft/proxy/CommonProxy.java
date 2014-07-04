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

	public int addArmor(String string) {
		return 0;
	}

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		if (guiId == ChrisLiuMod.GUI_TELEPORT)  {
			return new ContainerTeleport(player, world);
		} else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		if (guiId == ChrisLiuMod.GUI_TELEPORT) {
			return new GuiTeleport(player, world);
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
