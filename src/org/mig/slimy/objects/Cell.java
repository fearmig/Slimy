package org.mig.slimy.objects;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.EntitySlime;

public class Cell extends EntitySlime {

	public Cell(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());
    }
}


