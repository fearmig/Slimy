package org.mig.slimy.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TitleMessageUtility {
	
	public static void sendTitle(Player player){
		
		String title = "&5Welcome to &aGC's";
		String subtitle = "&aSlimy";
		title = title.replace('&', '§');
		subtitle = subtitle.replace('&', '§');
		IChatBaseComponent titleTitle = ChatSerializer.a("{\"text\": \""+title+"\"}");
		((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, titleTitle, 10, 20, 10));
		
		IChatBaseComponent subTitleTitle = ChatSerializer.a("{\"text\": \""+subtitle+"\"}");
		((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subTitleTitle, 10, 20, 10));
		
		
		/*if(title.getHeader()!=null && title.getFooter()!=null){
			title.setHeader(title.getHeader().replace('&', '§'));
			title.setFooter(title.getFooter().replace('&', '§'));
			IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title.getHeader() + "\"}");
			IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title.getFooter() + "\"}");
			PacketPlayOutPlayerListHeaderFooter tabDisplayPacket 
					= new PacketPlayOutPlayerListHeaderFooter(tabHeader);
			
			try {
	            Field field = tabDisplayPacket.getClass().getDeclaredField("b");
	            field.setAccessible(true);
	            field.set(tabDisplayPacket, tabFoot);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	((CraftPlayer) player).getHandle().playerConnection
        			.sendPacket(tabDisplayPacket);
	        }
		}*/
	}
}
