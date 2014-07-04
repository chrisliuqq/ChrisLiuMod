package net.chrisliu.minecraft.command;

import java.util.List;

import net.chrisliu.minecraft.ChrisLiuMod;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandHome implements ICommand {

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender arg0, String[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List getCommandAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandName() {
		return "home";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "home";
	}

	@Override
	public boolean isUsernameIndex(String[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {		
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(arg0.getCommandSenderName());
		player.setPositionAndUpdate(ChrisLiuMod.d0, ChrisLiuMod.d1, ChrisLiuMod.d2);
	}

}

