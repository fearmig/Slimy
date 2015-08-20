package org.mig.slimy.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.mig.slimy.Slimy;
import org.mig.slimy.listeners.PlayerListeners;
import org.mig.slimy.objects.Arena;

public class ArenaModule {
	
	private static Arena arena;
	private Slimy main;
	
	public ArenaModule(Slimy main){
		this.main = main;
	}
	
	public void load(){
		if(!main.getConfig().contains("spawn.x"))
			return;
		
		arena = new Arena();
		arena.setWorld(main.getConfig().getString("world"));
		arena.setSpawn(new Location(
				Bukkit.getWorld(main.getConfig().getString("world")),
				main.getConfig().getDouble("spawn.x"),
				main.getConfig().getDouble("spawn.y"),
				main.getConfig().getDouble("spawn.z"),
				(float)main.getConfig().getDouble("spawn.yaw"),
				(float)main.getConfig().getDouble("spawn.pitch")));
		
		PlayerListeners.setY(arena.getSpawn().getBlockY()+10);
	}
	
	public void createArena(Location loc){
		arena = new Arena();
		arena.setWorld(loc.getWorld().getName());
		arena.setSpawn(loc);
		
		main.getConfig().set("world", loc.getWorld().getName());
		main.getConfig().set("spawn.x",loc.getX());
		main.getConfig().set("spawn.y",loc.getY());
		main.getConfig().set("spawn.z",loc.getZ());
		main.getConfig().set("spawn.yaw",(double)loc.getYaw());
		main.getConfig().set("spawn.pitch",(double)loc.getPitch());
		main.saveConfig();
		PlayerListeners.setY(loc.getBlockY()+10);
	}
	
	public static Arena getArena(){
		return arena;
	}
	
	public void refresh(){
		arena=null;
		load();
	}
}
