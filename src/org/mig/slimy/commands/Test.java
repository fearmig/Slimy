package org.mig.slimy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mig.slimy.Slimy;
import org.mig.slimy.listeners.PlayerListeners;
import org.mig.slimy.modules.ArenaModule;
import org.mig.slimy.modules.GameModule;
import org.mig.slimy.utils.FoodUtil;

public class Test implements CommandExecutor{

	private Slimy main;
	
	public Test(Slimy main){
		this.main = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		GameModule gm = new GameModule(main);
		if(args.length==1){
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
		}
		else{
			gm.joinGame((Player)sender);
			return true;
		}
		return true;
	}
}
