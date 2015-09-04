package org.mig.slimy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mig.slimy.commands.SlimyCommands;
import org.mig.slimy.listeners.ClickListener;
import org.mig.slimy.listeners.PlayerListeners;
import org.mig.slimy.listeners.WeatherListener;
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
		
		getCommand("slime").setExecutor(new SlimyCommands(this));
		
		getServer().getPluginManager().registerEvents(new PlayerListeners(this),this);
		getServer().getPluginManager().registerEvents(new WeatherListener(),this);
		//getServer().getPluginManager().registerEvents(new ClickListener(this),this);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				Bukkit.getWorld("slimy").setTime(6*1000);
			}
			
		}.runTaskTimer(this, 1, 1);
	}

	public void onDisable(){
		
	}
}
