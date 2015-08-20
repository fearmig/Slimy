package org.mig.slimy.objects;

import java.util.HashMap;

import org.bukkit.scoreboard.Scoreboard;

public class Game {
	
	private HashMap<String, String> players;
	private HashMap<String, Integer> masses;
	private HashMap<String, Scoreboard> scoreboard;
	
	/**
	 * @return the players
	 */
	public HashMap<String, String> getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(HashMap<String, String> players) {
		this.players = players;
	}
	
	/**
	 * @return the masses
	 */
	public HashMap<String, Integer> getMasses() {
		return masses;
	}
	/**
	 * @param masses the masses to set
	 */
	public void setMasses(HashMap<String, Integer> masses) {
		this.masses = masses;
	}
	/**
	 * @return the scoreboard
	 */
	public HashMap<String, Scoreboard> getScoreboard() {
		return scoreboard;
	}
	/**
	 * @param scoreboard the scoreboard to set
	 */
	public void setScoreboard(HashMap<String, Scoreboard> scoreboard) {
		this.scoreboard = scoreboard;
	}
}
