package org.mig.slimy.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.mig.slimy.Slimy;
import org.mig.slimy.modules.ArenaModule;
import org.mig.slimy.modules.GameModule;

public class PlayerListeners implements Listener {
	private static int yLevel;
	private int maxX = 50;
	private int minX = -50;
	private int maxZ = 50;
	private int minZ = -50;
	private Slimy main;
	private static ArrayList<Player> players = new ArrayList<>();
	
	public PlayerListeners(Slimy main){
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemMergeEvent(ItemMergeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		event.getPlayer().setGameMode(GameMode.SPECTATOR);
		event.getPlayer().setFlySpeed((float) .05);
		event.getPlayer().teleport(ArenaModule.getArena().getSpawn());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerToggleSprintEvent(final PlayerToggleSprintEvent event) {
		
		if(!players.contains(event.getPlayer()))
			return;
		
		Bukkit.broadcastMessage("Debug: " + event.getPlayer().getName() + " was denied sprinting and is now disabled");
		
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 100));
		
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		int tempX = event.getPlayer().getLocation().getBlockX();
		int tempZ = event.getPlayer().getLocation().getBlockZ();
		float tempYaw = event.getPlayer().getLocation().getYaw();
		float tempPitch = event.getPlayer().getLocation().getPitch();
		int tempY;
		try{
			tempY = (int) (Math.ceil(GameModule.getGame().getMasses().get(event.getPlayer().getUniqueId().toString())/400))+yLevel;
		}catch(NullPointerException e){
			tempY=yLevel;
		}
		if (((event.getPlayer().getLocation().getBlockY() <= (tempY-1))
				|| event.getPlayer().getLocation().getBlockY() >= (tempY+1))
				&& players.contains(event.getPlayer())) {	
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), tempX,
					tempY, tempZ, tempYaw, tempPitch));
		} else if (tempX >= maxX){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), maxX-1,
					tempY, tempZ, tempYaw, tempPitch));
		} else if (tempX <= minX){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), minX+1,
					tempY, tempZ, tempYaw, tempPitch));
		} else if (tempZ >= maxZ){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), tempX,
					tempY, maxZ-1, tempYaw, tempPitch));
		} else if (tempZ <= minZ){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), tempX,
					tempY, minZ+1, tempYaw, tempPitch));
		}
	}
	
	public static void setY(int y){
		yLevel = y;
	}
	
	public static int getY(){
		return yLevel;
	}
	
	public static void addPlayer(Player p){
		players.add(p);
	}
	
	public static void removePlayer(Player p){
		players.remove(p);
	}
	
	public static void clearPlayers(){
		players.clear();
	}
	
	public static ArrayList<Player> getPlayers(){
		return players;
	}

}
