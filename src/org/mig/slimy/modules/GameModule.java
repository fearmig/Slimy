package org.mig.slimy.modules;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMagmaCube;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mig.slimy.Slimy;
import org.mig.slimy.listeners.PlayerListeners;
import org.mig.slimy.objects.Game;
import org.mig.slimy.utils.CellUtil;
import org.mig.slimy.utils.FoodUtil;

public class GameModule {
	private static boolean inGame = false;
	private static Game game;
	private Slimy main;
	
	public GameModule(Slimy main){
		this.main = main;
	}
	
	public void newGame(){
		game = new Game();
		game.setPlayers(new HashMap<String,LivingEntity>());
		game.setMasses(new HashMap<String,Integer>());
		FoodUtil.placeInitalFood();
	}
	
	public void joinGame(final Player p){
		Random random = new Random();
		p.setGameMode(GameMode.CREATIVE);
		p.setFlying(true);
		CellUtil cu = new CellUtil();
		int size=2;
		int newSize = random.nextInt(15 - 5 + 1) + 5;
		int x = random.nextInt(73 - (-73) + 1) - 73;
		int z = random.nextInt(73 - (-73) + 1) - 73;
		Location loc = ArenaModule.getArena().getSpawn();
		Location spawnLoc = new Location(loc.getWorld(),x,loc.getY(),z,loc.getYaw(),loc.getPitch());
		LivingEntity le = null;
		
		p.teleport(spawnLoc.add(0, 10, 0));
		PlayerListeners.addPlayer(p);
		/*
		 * x:7     y:.1
		 * x:5000  y:0
		 */
		p.setFlySpeed((float) (( -0.00002002803925495694*Math.abs(newSize)) + 0.10014019627478471));
		
		UUID u= cu.spawn(spawnLoc.subtract(0, 10, 0), size).getUniqueID();
		for(Entity e: p.getNearbyEntities(.1, 50, .1)){
			if(e instanceof CraftSlime){
				if(e.getUniqueId().equals(u)){
					le = (LivingEntity) e;
					break;
				}
			}
		}
		if(le==null)
			return;
		
		game.getPlayers().put(p.getUniqueId().toString(), le);
		game.getMasses().put(p.getName(), newSize);
		
		//add the player to the ListenTo list for player listeners
		
		/*
		 * start the runnable that will check for a players position in reference to their
		 * slime and teleport the slime to the players position to simulate the player moving
		 * the slime.
		 */
		new BukkitRunnable(){
			String uuid = p.getUniqueId().toString();
			public void run(){
				if(p.isOnline() && game.getPlayers().get(uuid)!=null){
					Location temploc = p.getLocation();
					temploc.setY(PlayerListeners.getY());
					game.getPlayers().get(uuid).teleport(temploc.subtract(0, 10, 0));
				}
				else{
					try{
						game.getPlayers().get(uuid).remove();
					}catch(NullPointerException e){
						game.getMasses().remove(p.getName());
						PlayerListeners.removePlayer(p);
						this.cancel();
					}
					game.getPlayers().remove(uuid);
					game.getMasses().remove(p.getName());
					PlayerListeners.removePlayer(p);
					this.cancel();
				}
			}
		}.runTaskTimer(main, 0, 3);
		
		new BukkitRunnable() {
			String uuid = p.getUniqueId().toString();

			public void run() {
				
				/*
				 * Removes a player if they are not online or their slime no longer exists
				 */
				if (!p.isOnline()) {
					try{
						game.getPlayers().get(uuid).remove();
					} catch(NullPointerException e){
						
					}
					game.getPlayers().remove(uuid);
					game.getMasses().remove(p.getName());
					PlayerListeners.removePlayer(p);
					this.cancel();
					return;
				} else if (game.getPlayers().get(uuid) == null) {
					game.getPlayers().remove(uuid);
					game.getMasses().remove(p.getName());
					PlayerListeners.removePlayer(p);
					this.cancel();
					return;
				}

				/*
				 * establish Mass and search radius associated with that mass
				 */
				int pMass = game.getMasses().get(p.getName());
				double searchRad = Math.abs(pMass) / 250;
				if (searchRad < .4)
					searchRad = .4;
				Entity entityA = game.getPlayers().get(uuid);
				
				/*
				 * Search for nearby entities and calculate if the player will eat those entities.
				 */
				int newSize = pMass;
				boolean alreadyNeg = false;
				for (Entity entityB : p.getNearbyEntities(searchRad, 50,
						searchRad)) {
					/*
					 * If the entity found is a Player check their mass against pMass
					 */
					if (entityB instanceof Player && !entityB.equals(p)) {
						//grab the found player's slime
						Entity otherSlime = game.getPlayers().get(
								entityB.getUniqueId().toString());
						if (otherSlime != null) {
							
							//grab the foudnd player's mass
							int oMass = game.getMasses().get(entityB.getName());

							//Bukkit.broadcastMessage("DEBUG: pMass: " + pMass);
							//Bukkit.broadcastMessage("DEBUG: oMass: " + oMass);

							if(Math.abs(pMass) > Math.abs(oMass) * 1.2){
								newSize = (int) (newSize + (oMass * .80));
							}else {
								return;
							}

							/*
							 * if there was a player found smaller then remove them
							 */
							if (newSize != pMass) {
								otherSlime.remove();
								PlayerListeners.removePlayer((Player) entityB);
								game.getPlayers().remove(
										entityB.getUniqueId().toString());
								game.getMasses().remove(entityB.getName());
								((HumanEntity) entityB)
										.setGameMode(GameMode.SPECTATOR);

								Bukkit.broadcastMessage(ChatColor.GREEN
										+ entityB.getName() + ChatColor.AQUA
										+ " was just consumed by "
										+ ChatColor.GREEN + p.getName());
							}
						}
					}
					//if the entity found is a small magmacube
					else if (entityB instanceof CraftMagmaCube
							&& ((CraftMagmaCube) entityB).getSize() == 1) {
						if(entityB!=null){
							entityB.remove();
							newSize = newSize - 5;
							FoodUtil.spawnMagmaFood();
						}
					} else if (entityB instanceof CraftSlime
							&& ((CraftSlime) entityB).getSize() == 1) {
						if(entityB!=null){
							entityB.remove();
							newSize = newSize + 1;
							FoodUtil.spawnSlimeFood();
						}
							
					}

				}// end of for loop through nearby entities
			
				LivingEntity le = null;
				UUID u = null;
				boolean swapped = false;
				if (pMass <= 0)
					alreadyNeg = true;
				
				/*
				 * Check to see if the player switched pieces and if so give them that piece
				 */
				if (newSize < 1 && !alreadyNeg) {
					Bukkit.broadcastMessage(ChatColor.GREEN
							+ p.getName() + ChatColor.AQUA
							+ " just converted into a magma cube!");
					Location spawnLoc = entityA.getLocation();
					entityA.remove();
					CellUtil cu = new CellUtil();
					u = cu.spawnMagma(spawnLoc, 2).getUniqueID();
					swapped = true;
				} else if (newSize > -1 && alreadyNeg){
					Bukkit.broadcastMessage(ChatColor.GREEN
							+ p.getName() + ChatColor.AQUA
							+ " just converted back into a slime!");
					Location spawnLoc = entityA.getLocation();
					entityA.remove();
					CellUtil cu = new CellUtil();
					u = cu.spawn(spawnLoc, 2).getUniqueID();
					swapped = true;
				}
				
				/*
				 * If the piece was switched attach it to the player again
				 */
				if(swapped){
					for (Entity e : p.getNearbyEntities(.5, 50, .5)) {
						if (e instanceof CraftSlime) {
							if (e.getUniqueId().equals(u)) {
								le = (LivingEntity) e;
								break;
							}
						}
					}
					if (le == null)
						this.cancel();
					
					entityA = le;
					game.getPlayers().put(p.getUniqueId().toString(), le);
				}
				
				/*
				 * if first swap from neg to pos or pos to neg give a starting amount.
				 */
				if (newSize > -1 && alreadyNeg) {
					if (newSize < 7)
						newSize = 7;
				} else if (newSize < 1 && !alreadyNeg) {
					if (newSize > -7)
						newSize = -7;
				}
				
				/*
				 * set the new size an the new flyspead for the player based on the new size
				 */
				if(newSize>0){
					((CraftSlime) entityA).setSize((int) Math
							.ceil(newSize / 100.0) + 1);
					p.setFlySpeed((float) (( -0.00002002803925495694*Math.abs(newSize)) + 0.10014019627478471));
				}
				else{
					((CraftSlime) entityA).setSize((int) Math
							.ceil((-(newSize)) / 100.0) + 1);
					p.setFlySpeed((float) (( -0.00002002803925495694*Math.abs(newSize)) + 0.10014019627478471));
				}
				game.getMasses().put(p.getName(), newSize);
			}
		}.runTaskTimer(main, 0, 7);
		
		new BukkitRunnable(){
			public void run(){
				inGame=true;
			}
		}.runTaskLater(main, 20);
		
	}
	
	public static boolean isInGame(){
		return inGame;
	}
	
	public void refresh(){
		for(Entity e : Bukkit.getWorld("slimy").getEntitiesByClasses(CraftSlime.class)){
			e.remove();
		}
		game = null;
		newGame();
	}
	
	public static Game getGame(){
		return game;
	}
}
