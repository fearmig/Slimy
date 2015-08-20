package org.mig.slimy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.mig.slimy.Slimy;
import org.mig.slimy.modules.ArenaModule;
import org.mig.slimy.modules.GameModule;
import org.mig.slimy.utils.CellUtil;

public class ClickListener  implements Listener {

	private Slimy main;
	CellUtil cu;
	
	public ClickListener(Slimy main){
		this.main = main;
		cu= new CellUtil();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Location loc = e.getPlayer().getLocation();
		loc.setY(ArenaModule.getArena().getSpawn().getY());
		//if(PlayerListeners.getPlayers().contains(e.getPlayer())){
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) ||
					e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				shootFood(e.getPlayer());
			
			else if(e.getAction().equals(Action.LEFT_CLICK_AIR) ||
					e.getAction().equals(Action.LEFT_CLICK_BLOCK))
				shootMass(loc, e.getPlayer());
		//}
	}

	private void shootFood(Player player) {
		Location loc = player.getLocation();
		String foodUUID = cu.spawn(loc, 1).toString();
		for(Entity entity : Bukkit.getWorld("slimy").getNearbyEntities(loc, .5, .5, .5)){
			if(entity.getUniqueId().toString().equals(foodUUID)){
				Bukkit.broadcastMessage("DEBUG: feed shot");
				
				Vector vecA = loc.toVector();
                Vector direction = loc.getDirection();
                Vector newVec = vecA.multiply(direction).normalize();
                Bukkit.broadcastMessage("DEBUG: X: " + newVec.getX());
                Bukkit.broadcastMessage("DEBUG: Y: " + newVec.getY());
                Bukkit.broadcastMessage("DEBUG: Z: " + newVec.getZ());
                newVec.multiply(10);
                Bukkit.broadcastMessage("DEBUG: X*10: " + newVec.getX());
                Bukkit.broadcastMessage("DEBUG: Y*10: " + newVec.getY());
                Bukkit.broadcastMessage("DEBUG: Z*10: " + newVec.getZ());
                newVec.setY(0);
                player.setVelocity(newVec);
                entity.setVelocity(newVec);
			}
		}
	}

	private void shootMass(Location loc, Player player) {
		int size = (int) Math.ceil(GameModule.getGame().getMasses().get(player.getName())/ 100.0)+1;
		String massUUID = cu.spawn(loc, size).toString();
		for(Entity entity : Bukkit.getWorld("slimy").getNearbyEntities(loc, .5, .5, .5)){
			if(entity.getUniqueId().toString().equals(massUUID)){
				Bukkit.broadcastMessage("DEBUG: mass shot");
				Vector vecA = loc.toVector();
                Vector direction = loc.getDirection();
                Vector newVec = vecA.multiply(direction).normalize();
               
                newVec.setY(0);
               
                entity.setVelocity(newVec);
			}
		}
	}
}
