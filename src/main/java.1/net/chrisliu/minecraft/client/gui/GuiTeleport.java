package net.chrisliu.minecraft.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.chrisliu.minecraft.client.KeyHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

@SideOnly(Side.CLIENT)
public class GuiTeleport extends GuiContainer{

	public GuiTeleport(EntityPlayer player) {
		super(new ContainerTeleport(player));
		// TODO Auto-generated constructor stub
		
		System.out.println("[debug] GuiTeleport GuiTeleport ");
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// This method will simply draw inventory's name on the screen - you could do without it entirely
		// if that's not important to you, since we are overriding the default inventory rather than
		// creating a specific type of inventory

		// with the name "Custom Inventory", the 'Cu' will be drawn in the first slot
		//fontRendererObj.drawString(s, xSize - fontRendererObj.getStringWidth(s), 12, 4210752);
		// this just adds "Inventory" above the player's inventory below
		fontRendererObj.drawString("Teleport Selector", 80, ySize - 96, 4210752);
		
		System.out.println("[debug] GuiTeleport drawGuiContainerForegroundLayer ");
		
	}
	
	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//mc.getTextureManager().bindTexture(iconLocation);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		//drawPlayerModel(guiLeft + 51, guiTop + 75, 30, guiLeft + 51 - xSize_lo, guiTop + 25 - ySize_lo, mc.thePlayer);
		
		System.out.println("[debug] GuiTeleport drawGuiContainerBackgroundLayer ");
	}
	
	@Override
	protected void keyTyped(char c, int keyCode) {
		super.keyTyped(c, keyCode);
		
		System.out.println("[debug] GuiTeleport keyTyped ");
		
		// 1 is the Esc key, and we made our keybinding array public and static so we can access it here
		if (c == 1 || keyCode == KeyHandler.keys[KeyHandler.KEY_END].getKeyCode()) {
			mc.thePlayer.closeScreen();
		}
	}

}
