package org.mig.slimy.listeners;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.mig.slimy.Slimy;
import org.mig.slimy.modules.ArenaModule;
import org.mig.slimy.modules.GameModule;
import org.mig.slimy.utils.TitleMessageUtility;

public class PlayerListeners implements Listener {
	private static int yLevel;
	private int maxX = 75;
	private int minX = -75;
	private int maxZ = 75;
	private int minZ = -75;
	private Slimy main;
	private static ArrayList<Player> players = new ArrayList<>();
	
	public PlayerListeners(Slimy main){
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		//send the title to the player
		TitleMessageUtility.sendTitle(event.getPlayer());
		
		event.getPlayer().setGameMode(GameMode.SPECTATOR);
		event.getPlayer().setFlySpeed((float) .05);
		event.getPlayer().teleport(ArenaModule.getArena().getSpawn());
		event.getPlayer().sendMessage(ChatColor.AQUA+"Do "+ ChatColor.GREEN + "/slime " + ChatColor.AQUA + "to start!");
		
		/*
		 * Fix for entities disappearing until i find a real fix
		 */
		if(players.size()==0){
			new BukkitRunnable(){
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					GameModule gm = new GameModule(main);
					ArenaModule am = new ArenaModule(main);
					am.refresh();
					gm.refresh();
				}
			}.runTaskLater(main, 60);
			
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerToggleSprintEvent(final PlayerToggleSprintEvent event) {
		
		if(!players.contains(event.getPlayer()))
			return;
		
		String uuid = event.getPlayer().getUniqueId().toString();
		
		try{
			GameModule.getGame().getPlayers().get(uuid).remove();
		} catch(NullPointerException e){
			
		}
		GameModule.getGame().getPlayers().remove(uuid);
		GameModule.getGame().getMasses().remove(event.getPlayer().getName());
		PlayerListeners.removePlayer(event.getPlayer());
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + event.getPlayer().getName()
				+ ChatColor.GREEN + " shouldn't have sprinted!");
		event.getPlayer().setSprinting(false);
		//event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 100));
		
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		event.getPlayer().setSprinting(false);
		int tempX = event.getPlayer().getLocation().getBlockX();
		int tempZ = event.getPlayer().getLocation().getBlockZ();
		int tempy2 = event.getPlayer().getLocation().getBlockY();
		float tempYaw = event.getPlayer().getLocation().getYaw();
		float tempPitch = event.getPlayer().getLocation().getPitch();
		int tempY;
		try{
			int size = GameModule.getGame().getMasses().get(event.getPlayer().getName());
			if(size<0)
				size = size*-1;
			
			tempY = (int) (Math.ceil(size/100))+yLevel;
		}catch(NullPointerException e){
			//Bukkit.broadcastMessage("DEBUG: nullpointer hit");
			tempY=yLevel;
		}
		if (((event.getPlayer().getLocation().getBlockY() < 1)
				|| event.getPlayer().getLocation().getBlockY() >= (tempY+1))
				&& players.contains(event.getPlayer())) {	
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), tempX,
					tempY, tempZ, tempYaw, tempPitch));
		} else if (tempX >= maxX){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), maxX-1,
					tempy2, tempZ, tempYaw, tempPitch));
		} else if (tempX <= minX){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), minX+1,
					tempy2, tempZ, tempYaw, tempPitch));
		} else if (tempZ >= maxZ){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), tempX,
					tempy2, maxZ-1, tempYaw, tempPitch));
		} else if (tempZ <= minZ){
			event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), tempX,
					tempy2, minZ+1, tempYaw, tempPitch));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClickEvent(InventoryClickEvent e){
		e.setCancelled(true);
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
