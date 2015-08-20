package org.mig.slimy.objects;

import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class MagmaCell extends EntityMagmaCube{

	public MagmaCell(World world) {
		super(((CraftWorld)world).getHandle());
    }
}
