package net.chrisliu.minecraft.client.gui;

import net.chrisliu.minecraft.ChrisLiuMod;
import net.chrisliu.minecraft.client.KeyHandler;
import net.chrisliu.minecraft.network.packet.TeleportPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTeleport extends GuiContainer
{
	/** x size of the inventory window in pixels. Defined as float, passed as int */
	private float xSize_lo;

	/** y size of the inventory window in pixels. Defined as float, passed as int. */
	private float ySize_lo;

	/** Normally I use '(ModInfo.MOD_ID, "textures/...")', but it can be done this way as well */
	private static final ResourceLocation iconLocation = new ResourceLocation("chrisliu:textures/gui/custom_inventory.png");

	private World _world;

	public GuiTeleport(EntityPlayer player, World world) {
		super(new ContainerTeleport(player, world));

		// if you need the player for something later on, store it in a local variable here as well
		this._world = world;
	}

	@Override
	protected void keyTyped(char c, int keyCode) {
		super.keyTyped(c, keyCode);
		// 1 is the Esc key, and we made our keybinding array public and static so we can access it here
		if (c == 1 || keyCode == KeyHandler.keys[KeyHandler.KEY_TELEPORT].getKeyCode()) {
			mc.thePlayer.closeScreen();
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xSize_lo = mouseX;
		ySize_lo = mouseY;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// This method will simply draw inventory's name on the screen - you could do without it entirely
		// if that's not important to you, since we are overriding the default inventory rather than
		// creating a specific type of inventory

		// this just adds "Inventory" above the player's inventory below
		fontRendererObj.drawString("==Teleport List==", 56, ySize - 160, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(iconLocation);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	
	public void initGui() {
		
		super.initGui();
		
		int posX = (int) ((this.width - xSize_lo)) / 2;
		int posY = ySize - 150;
		
		int i = 0;
		/*
		for( String name : MinecraftServer.getServer().getConfigurationManager().getAllUsernames()) {
			
			if (Minecraft.getMinecraft().thePlayer.getDisplayName().equals(name)) continue;
			
			this.buttonList.add(new GuiButton(i++, posX - 50, posY, 100, 20, name));
			posY += 25;
		}
		*/
		
		for( Object o : _world.playerEntities ) {
			EntityPlayer player = (EntityPlayer) o;
			//player.getDisplayName();
			
			if (Minecraft.getMinecraft().thePlayer.getDisplayName().equals(player.getDisplayName())) continue;
			
			this.buttonList.add(new GuiButton(i++, posX - 50, posY, 100, 20, player.getDisplayName()));
			posY += 25;
			
		}
		
		this.buttonList.add(new GuiButton(i++, posX - 50, posY, 100, 20, "home"));
		posY += 25;
	}
	
	public void actionPerformed(GuiButton button) {
		
		String targetPlayerName = button.displayString;
		
		//Minecraft.getMinecraft().thePlayer.sendChatMessage("You select [" + targetPlayerName + "]");
		
		ChrisLiuMod.packetPipeline.sendToServer(new TeleportPacket(targetPlayerName));
		
	}
	
}
