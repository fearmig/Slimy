package org.mig.slimy.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.mig.slimy.Slimy;
import org.mig.slimy.modules.GameModule;

import be.maximvdw.featherboard.api.PlaceholderAPI;
import be.maximvdw.featherboard.api.PlaceholderAPI.PlaceholderRequestEvent;
import be.maximvdw.featherboard.api.PlaceholderAPI.PlaceholderRequestEventHandler;
import be.maximvdw.featherboardcore.placeholders.Placeholder;

public class ScoreBoardUtil {
	
	private Slimy main;
	
	private static String top10List[] = 
		{
		"01                  ",
		"02                  ",
		"03                  ",
		"04                  ",
		"05                  ",
		"06                  ",
		"07                  ",
		"08                  ",
		"09                  ",
		"10                  "
		};
	
	public ScoreBoardUtil(Slimy main){
		this.main = main;
	}
	
	public void init() {
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy1", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[0];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy2", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[1];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy3", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[2];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy4", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[3];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy5", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[4];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy6", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[5];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy7", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[6];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy8", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[7];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy9", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[8];
			}
		});
		
		PlaceholderAPI.registerOfflinePlaceholder("gcslimy10", true,
				new PlaceholderRequestEventHandler(){
			@Override
			public String onPlaceholderRequest(PlaceholderRequestEvent event){
				return top10List[9];
			}
		});
	}
	
	public void updateScoreboard(){
		new BukkitRunnable(){

			@Override
			public void run() {
				ArrayList<Integer> sizes = new ArrayList<>();
				ArrayList<String> names = new ArrayList<>();
				sizes.clear();
				names.clear();
				HashMap<String, Integer> masses = GameModule.getGame().getMasses();
				
				int count = 0;
				for(String s: masses.keySet()){
					//System.out.println(masses.keySet().size()+"");
					//System.out.println(sizes.size()+"");
					//System.out.println(names.size()+"");
					int sMass = masses.get(s);
					if(sizes.size()==0){
						sizes.add(sMass);
						names.add(s);
					} else if (sizes.size()==1){
						if(sMass > sizes.get(0)){
							sizes.add(0, sMass);
							names.add(0, s);
						} else {
							sizes.add(sMass);
							names.add(s);
						}
					} else {
						for (int i = 0; i < 10; i++) {
							if (i < sizes.size()) {
								if (sMass > sizes.get(i)) {
									// System.out.println("Putting "+ s +
									// " in front of " + names.get(i));
									sizes.add(i, sMass);
									names.add(i, s);
									break;
								}
							} else if (count >= sizes.size()
									&& count >= names.size()) {
								sizes.add(sMass);
								names.add(s);
								break;
							}
						}
					}
					count++;
				}
				
				for(int y = 0; y < 10; y++){
					if(y!=9){
						if(names.size()>y && sizes.size()>y){
							if (names.get(y).length()>12)
								top10List[y]="0"+(y+1)+" "+ formatN(ChatColor.AQUA+names.get(y).substring(0,11)) 
										+ formatS(sizes.get(y));
							else
								top10List[y]="0"+(y+1)+" "+ formatN(ChatColor.AQUA+names.get(y))
										+ formatS(sizes.get(y));	
						}
						else{
							top10List[y]="0"+(y+1)+" "+ formatN(ChatColor.AQUA+"empty_slot") + formatS(0);
						}
					}
					else{
						if(names.size()>y && sizes.size()>y){
							if (names.get(y).length()>11)
								top10List[y]="10 "+ formatN(ChatColor.AQUA+names.get(y).substring(0,11)) 
										+ formatS(sizes.get(y));
							else
								top10List[y]="10 "+ formatN(ChatColor.AQUA+names.get(y)) 
										+ formatS(sizes.get(y));
						}
						else{
							top10List[y]="10 "+ formatN(ChatColor.AQUA+"empty_slot") + formatS(0);
						}
					}
				}

			}
			private String formatN(String name) {
				name = name + ChatColor.WHITE;
					
				for(int i=name.length()-1; i<17; i++){
					name = name+"_";
				}
				return name;
			}
			
			private String formatS(int size) {
				if(size<10)
					return ChatColor.AQUA + "0000" + size;
				else if(size < 100)
					return ChatColor.AQUA + "000" + size;
				else if(size < 1000)
					return ChatColor.AQUA + "00" + size;
				else if(size < 10000)
					return ChatColor.AQUA + "0" + size;
				else if(size < 100000)
					return ChatColor.AQUA + "" + size;
				else
					return ChatColor.AQUA + "MAXED";
			}
			
		}.runTaskTimerAsynchronously(main, 0, 20);
	}
}
