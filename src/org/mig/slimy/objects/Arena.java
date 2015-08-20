package org.mig.slimy.objects;

import org.bukkit.Location;

public class Arena {
	
	private Location spawn;
	private String world;
	
	/**
	 * @return the spawn
	 */
	public Location getSpawn() {
		return spawn;
	}
	/**
	 * @param spawn the spawn to set
	 */
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	/**
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}
	/**
	 * @param world the world to set
	 */
	public void setWorld(String world) {
		this.world = world;
	}
}
