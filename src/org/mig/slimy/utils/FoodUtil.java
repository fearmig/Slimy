package org.mig.slimy.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.mig.slimy.modules.ArenaModule;

public class FoodUtil {
	
	public static void placeInitalFood(){
		
		Random random = new Random();
		CellUtil cu = new CellUtil();
		
		for(int i=0; i < 1400; i++){
			int x = random.nextInt(74 - (-74) + 1) - 74;
			int z = random.nextInt(74 - (-74) + 1) - 74;
			//ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
			Location loc = new Location(Bukkit.getWorld("slimy"),x,ArenaModule.getArena().getSpawn().getY(),z);
			//Bukkit.getWorld("slimy").dropItem(loc, item);
			
			cu.spawn(loc, 1);
			//System.out.println("Slime placed at x:" + x+ " y: " +loc.getY() + " z:"+ z);
			
		}
		for(int i=0; i < 400; i++){
			int x = random.nextInt(74 - (-74) + 1) - 74;
			int z = random.nextInt(74 - (-74) + 1) - 74;
			//ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
			Location loc = new Location(Bukkit.getWorld("slimy"),x,ArenaModule.getArena().getSpawn().getY(),z);
			//Bukkit.getWorld("slimy").dropItem(loc, item);
			
			cu.spawnMagma(loc, 1);
			//System.out.println("Magma placed at x:" + x+ " y: " +loc.getY() + " z:"+ z);
		}
		
	}
	
	public static void spawnMagmaFood(){
		Random random = new Random();
		CellUtil cu = new CellUtil();
		int x = random.nextInt(74 - (-74) + 1) - 74;
		int z = random.nextInt(74 - (-74) + 1) - 74;
		//ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
		Location loc = new Location(Bukkit.getWorld("slimy"),x,ArenaModule.getArena().getSpawn().getY(),z);
		//Bukkit.getWorld("slimy").dropItem(loc, item);
		
		cu.spawnMagma(loc, 1);
		//System.out.println("Magma placed at x:" + x+ " y: " +loc.getY() + " z:"+ z);
	}
	
	public static void spawnSlimeFood(){
		Random random = new Random();
		CellUtil cu = new CellUtil();
		int x = random.nextInt(74 - (-74) + 1) - 74;
		int z = random.nextInt(74 - (-74) + 1) - 74;
		//ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
		Location loc = new Location(Bukkit.getWorld("slimy"),x,ArenaModule.getArena().getSpawn().getY(),z);
		//Bukkit.getWorld("slimy").dropItem(loc, item);
		
		cu.spawn(loc, 1);
		//System.out.println("Slime placed at x:" + x+ " y: " +loc.getY() + " z:"+ z);
	}
	
	
}
