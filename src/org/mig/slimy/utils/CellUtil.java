package org.mig.slimy.utils;

import java.util.UUID;

import org.bukkit.Location;
import org.mig.slimy.enums.CustomEntities;
import org.mig.slimy.objects.Cell;
import org.mig.slimy.objects.MagmaCell;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class CellUtil {

	private static int greenSlime = 600;
	private static int magmaSlime = 400;
	
	public UUID spawn(Location l, int size) {
		Cell c = new Cell(l.getWorld());
		disableAI(c);
		c.setSize(size);
		CustomEntities.spawnEntity(c, l);
        System.out.println("Placed slime!");
        return c.getUniqueID();
    }
	
	public UUID spawnMagma(Location l, int size) {
		MagmaCell c = new MagmaCell(l.getWorld());
		disableAI(c);
		c.setSize(size);
		CustomEntities.spawnEntity(c, l);
        System.out.println("Placed Magma!");
        return c.getUniqueID();
    }
	
	public void disableAI(Entity entity) {
	    net.minecraft.server.v1_8_R3.Entity nmsEnt = entity;
	    NBTTagCompound tag = nmsEnt.getNBTTag();
	    if(tag == null) {
	        tag = new NBTTagCompound();
	    }
	     
	    nmsEnt.c(tag);
	    tag.setInt("NoAI", 1);
	    nmsEnt.f(tag);
	}
}
