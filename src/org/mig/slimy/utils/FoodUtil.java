package org.mig.slimy.utils;

import java.util.Random;

import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.mig.slimy.modules.ArenaModule;

public class FoodUtil {
	
	public static void placeInitalFood(){
		
		Random random = new Random();
		CellUtil cu = new CellUtil();
		
		for(int i=0; i < 1100; i++){
			int x = random.nextInt(74 - (-74) + 1) - 74;
			int z = random.nextInt(74 - (-74) + 1) - 74;
			//ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
			Location loc = new Location(Bukkit.getWorld("slimy"),x,ArenaModule.getArena().getSpawn().getY(),z);
			//Bukkit.getWorld("slimy").dropItem(loc, item);
			WorldServer world = ((org.bukkit.craftbukkit.v1_8_R3.CraftWorld) loc.getWorld()).getHandle();
		       
	        EntitySlime slime = new EntitySlime(world);
	        slime.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	       
	        PacketPlayOutSpawnEntityLiving packedt = new PacketPlayOutSpawnEntityLiving(slime);
	       
	        p.getHandle().playerConnection.sendPacket(packedt);
	        vil = villager;
			
			cu.spawn(loc, 1);
			//System.out.println("Slime placed at x:" + x+ " y: " +loc.getY() + " z:"+ z);
			
		}
		for(int i=0; i < 200; i++){
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
