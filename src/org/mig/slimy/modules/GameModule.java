package org.mig.slimy.modules;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMagmaCube;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
import org.bukkit.entity.Entity;
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
		game.setPlayers(new HashMap<String,String>());
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
		
		game.getPlayers().put(p.getUniqueId().toString(), (cu.spawn(spawnLoc, size).toString()));
		game.getMasses().put(p.getName(), newSize);
		
		p.teleport(spawnLoc.add(0, 10, 0));
		PlayerListeners.addPlayer(p);
		p.setFlySpeed((float) ((-0.000030060120240480967*newSize) + 0.0851503006012024));
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
					movePeice(uuid, temploc.subtract(0, 10, 0));
					//if(p.getName().equalsIgnoreCase("FearMig"))
						//System.out.println("move task");
				}
				else{
					String piece = game.getPlayers().get(uuid);
					for(Entity tempE: p.getNearbyEntities( 3, 100, 3)){
						if(tempE.getUniqueId().toString().equals(piece))
							tempE.remove();
					}
					game.getPlayers().remove(uuid);
					game.getMasses().remove(p.getName());
					PlayerListeners.removePlayer(p);
					this.cancel();
				}
			}
		}.runTaskTimer(main, 0, 3);
		
		new BukkitRunnable(){
			World world = Bukkit.getWorld("slimy");
			String uuid = p.getUniqueId().toString();
			public void run(){
				if(!p.isOnline()){
					String piece = game.getPlayers().get(uuid);
					for(Entity tempE: Bukkit.getWorld("slimy").getEntities()){
						if(tempE.getUniqueId().toString().equals(piece))
							tempE.remove();
					}
					game.getPlayers().remove(uuid);
					game.getMasses().remove(p.getName());
					PlayerListeners.removePlayer(p);
					this.cancel();
				}
				else if(game.getPlayers().get(uuid)==null){
					game.getPlayers().remove(uuid);
					game.getMasses().remove(p.getName());
					PlayerListeners.removePlayer(p);
					//if(p.getName().equalsIgnoreCase("FearMig"))
						//System.out.println("calc task cancelled");
					this.cancel();
				}
					
				//if(p.getName().equalsIgnoreCase("FearMig"))
					//System.out.println("calc task");
				
				int pMass = game.getMasses().get(p.getName());
				double searchRad= pMass/250;
				if(searchRad<.4)
					searchRad=.4;
				if((p.getNearbyEntities(searchRad, 100, searchRad)).size()>0){
					boolean foundPlayerSlime = false;
					for(Entity entityA : p.getNearbyEntities(.2, 100, .2)){
						if(entityA instanceof CraftSlime && ((CraftSlime)entityA).getSize()>1){
							//find players slime
							if(game.getPlayers().get(p.getUniqueId().toString())
									.equals(entityA.getUniqueId().toString())){
								foundPlayerSlime = true;
							}
						}
						if(foundPlayerSlime){
							for(Entity entityB : p.getNearbyEntities(searchRad, 100, searchRad)){
								if(entityB instanceof CraftSlime && ((CraftSlime)entityB).getSize()>1 
											&& !entityB.equals(entityA)){
										boolean result=false;
									for(String s: game.getPlayers().keySet()){
										if(game.getPlayers().get(s).equals(entityB.getUniqueId().toString())){
											Player oPlayer = Bukkit.getPlayer(UUID.fromString(s));
											int oMass = game.getMasses().get(oPlayer.getName());
											result=true;
											int newSize = 0;
											
											if(pMass>(oMass*1.1))
												newSize = (int) (pMass + (oMass*.5));
											else 
												return;
											
											game.getMasses().put(p.getName(), newSize);
											p.setFlySpeed((float) ((-0.000030060120240480967*newSize) + 0.0851503006012024));
											if(newSize!=0){
												entityB.remove();
												PlayerListeners.removePlayer(oPlayer);
												game.getPlayers().remove(oPlayer.getUniqueId().toString());
												game.getMasses().remove(oPlayer.getName());
												oPlayer.setGameMode(GameMode.SPECTATOR);
												((CraftSlime)entityA).setSize((int) Math.ceil(newSize / 100.0)+1);
												Bukkit.broadcastMessage(ChatColor.GREEN+oPlayer.getName()+ChatColor.AQUA+
														" was just consumed by "+ ChatColor.GREEN+p.getName());
											}
										}
									}
									if(!result){
										if(((CraftSlime)entityB).getSize()<=((CraftSlime)entityA).getSize()){
											int newSize = ((CraftSlime)entityB).getSize()*50+ pMass;
											game.getMasses().put(p.getName(), newSize);
											entityB.remove();
											((CraftSlime)entityA).setSize((int) Math.ceil(newSize / 100.0)+1);
										}
									}
								}
								else if(entityB instanceof CraftMagmaCube && ((CraftMagmaCube)entityB).getSize()==1){
									entityB.remove();
									int newSize = game.getMasses().get(p.getName())-5;
									if (newSize<5){
										entityA.remove();
										game.getPlayers().remove(uuid);
										game.getMasses().remove(p.getName());
										PlayerListeners.removePlayer(p);
									}
									game.getMasses().put(p.getName(), newSize);
									p.setFlySpeed((float) ((-0.000030060120240480967*newSize) + 0.0851503006012024));
									((CraftSlime)entityA).setSize((int) Math.ceil(newSize / 100.0)+1);
									//do pick up bad confetti stuff
									FoodUtil.spawnMagmaFood();
								}
								else if(entityB instanceof CraftSlime && ((CraftSlime)entityB).getSize()==1){
									entityB.remove();
									int newSize = game.getMasses().get(p.getName())+1;
									game.getMasses().put(p.getName(), newSize);
									p.setFlySpeed((float) ((-0.000030060120240480967*newSize) + 0.0851503006012024));
									((CraftSlime)entityA).setSize((int) Math.ceil(newSize / 100.0)+1);
									//do pick up confetti stuff
									FoodUtil.spawnSlimeFood();
								}
							}
							break;
						}
					}
				}
			}
		}.runTaskTimer(main, 0, 5);
		
		new BukkitRunnable(){
			public void run(){
				inGame=true;
			}
		}.runTaskLater(main, 20);
		
	}
	
	public static void movePeice(String uuid, Location loc){
		//Bukkit.broadcastMessage("Debug: looking through entities");
		for(Entity e : Bukkit.getWorld("slimy").getEntitiesByClasses(CraftSlime.class)){
			//Bukkit.broadcastMessage("Debug: " + e.getUniqueId().toString());
			if(e.getUniqueId().toString().equals(game.getPlayers().get(uuid))){
				e.teleport(loc);
				return;
			}
		}
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
