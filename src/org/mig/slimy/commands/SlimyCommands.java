package org.mig.slimy.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mig.slimy.Slimy;
import org.mig.slimy.listeners.PlayerListeners;
import org.mig.slimy.modules.ArenaModule;
import org.mig.slimy.modules.GameModule;
import org.mig.slimy.utils.FoodUtil;

public class SlimyCommands implements CommandExecutor{

	private Slimy main;
	
	public SlimyCommands(Slimy main){
		this.main = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		GameModule gm = new GameModule(main);
		if(args.length==1 && sender.hasPermission("Slimy.admin")){
			if(args[0].equals("create")){
				ArenaModule am = new ArenaModule(main);
				am.createArena(((Player)sender).getLocation());
				return true;
			}
			else if(args[0].equals("refresh")){
				ArenaModule am = new ArenaModule(main);
				am.refresh();
				gm.refresh();
				PlayerListeners.clearPlayers();
			}	
			else if(args[0].equals("spawnfood")){
				FoodUtil.placeInitalFood();
			}
			else if(args[0].equals("help")){
				helpMessage(sender);
			}
		}
		else if(!PlayerListeners.getPlayers().contains(sender)){
			gm.joinGame((Player)sender);
			return true;
		}
		return true;
	}
	
	
	private void helpMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN +"Slimy"+ ChatColor.WHITE +" in "+ ChatColor.GREEN +"FFA "+ ChatColor.WHITE 
				 +"mode is a fight to be and stay the largest!");
		sender.sendMessage("Collect the " + ChatColor.RED +"Magma Cubes " + ChatColor.WHITE + " to take your score negative and"
				+ " become a virus to reduce other's mass.");
		sender.sendMessage("Or collect the "+ ChatColor.GREEN +"Slimes"+ ChatColor.WHITE + " to keep your mass positive and fight"
				+ " to stay on top");
		sender.sendMessage("");
		sender.sendMessage("Do "+ ChatColor.GREEN + "/slimy " + ChatColor.WHITE +"to start the game!");
	}
}
