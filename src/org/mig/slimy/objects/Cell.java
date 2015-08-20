package org.mig.slimy.objects;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.EntitySlime;

public class Cell extends EntitySlime {

	public Cell(World world) {
		super(((CraftWorld)world).getHandle());

    }
}


