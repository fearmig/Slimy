package org.mig.slimy.enums;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.mig.slimy.objects.Cell;
import org.mig.slimy.objects.MagmaCell;

public enum CustomEntities {
    //NAME("Entity name", Entity ID, yourcustomclass.class);
    CELL("Slime", 55, Cell.class),
    MAGMACELL("LavaSlime", 62, MagmaCell.class);

    private CustomEntities(String name, int id, Class<? extends Entity> custom) {
        addToMaps(custom, name, id);
    }
   
    public static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }
   
    public static void spawnEntity(Entity entity, Location loc) {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
    }

    @SuppressWarnings("unchecked")
    private static void addToMaps(Class<?> clazz, String name, int id) {
    	
    	((Map<String, Class<?>>)getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map<Class<?>, String>)getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        ((Map<Integer, Class<?>>)getPrivateField("e", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
    	((Map<Class<?>, Integer>)getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
    	((Map<String, Integer>)getPrivateField("g", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    	//((Map)getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        //((Map)getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        //((Map)getPrivateField("e", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
        //((Map)getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
        //((Map)getPrivateField("g", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, Integer.valueOf(id));
         
        
    }
}
