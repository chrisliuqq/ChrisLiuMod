package net.chrisliu.minecraft.network.packet;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.chrisliu.minecraft.ChrisLiuMod;
import net.chrisliu.minecraft.network.AbstractPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class TeleportPacket extends AbstractPacket
{
	// this will store the id of the gui to open
	private String id;

	// The basic, no-argument constructor MUST be included to use the new automated handling
	public TeleportPacket() {}
	
	// if there are any class fields, be sure to provide a constructor that allows
	// for them to be initialized, and use that constructor when sending the packet
	public TeleportPacket(String id) {
		//System.out.println("[debug] TeleportPacket TeleportPacket id = [" + id + "]");
		this.id = id;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		// basic Input/Output operations, very much like DataOutputStream
		buffer.writeBytes(id.getBytes());
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		try {
			id = new String(buffer.array(), "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			id = "home";
		}
		
		//System.out.println("[debug] TeleportPacket decodeInto id = [" + this.id + "]");
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// for opening a GUI, we don't need to do anything here
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		
		//System.out.println("[debug] TeleportPacket handleServerSide id = [" + this.id + "]");
		
		// because we sent the gui's id with the packet, we can handle all cases with one line:
		// player.openGui(ChrisLiuMod.instance, id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
		if (this.id.equals("home")) {			
			player.setPositionAndUpdate(ChrisLiuMod.d0, ChrisLiuMod.d1, ChrisLiuMod.d2);
		}
		else {
			EntityPlayer targetPlayer = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(this.id);
						
			if (targetPlayer != null) {
				player.setPositionAndUpdate(targetPlayer.posX, targetPlayer.posY, targetPlayer.posZ);
			}
		}
		
		player.closeScreen();
	}
}
