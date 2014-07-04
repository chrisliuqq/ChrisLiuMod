package net.chrisliu.minecraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerTeleport extends Container {

	public ContainerTeleport(EntityPlayer player) {
		System.out.println("[debug] ContainerTeleport ContainerTeleport ");
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
