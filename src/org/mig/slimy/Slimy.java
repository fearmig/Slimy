package org.mig.slimy;

import org.bukkit.plugin.java.JavaPlugin;
import org.mig.slimy.commands.Test;
import org.mig.slimy.listeners.ClickListener;
import org.mig.slimy.listeners.PlayerListeners;
import org.mig.slimy.modules.ArenaModule;
import org.mig.slimy.modules.GameModule;
import org.mig.slimy.utils.ScoreBoardUtil;

public class Slimy extends JavaPlugin{
	
	
	public void onEnable(){
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		ArenaModule am = new ArenaModule(this);
		am.load();
		
		GameModule gm = new GameModule(this);
		ScoreBoardUtil su = new ScoreBoardUtil(this);
		
		if(this.getConfig().contains("spawn.x")){
			gm.newGame();
			su.init();
			su.updateScoreboard();
		}
		
		getCommand("slime").setExecutor(new Test(this));
		
		getServer().getPluginManager().registerEvents(new PlayerListeners(this),this);
		//getServer().getPluginManager().registerEvents(new ClickListener(this),this);
	}

	public void onDisable(){
		
	}
}