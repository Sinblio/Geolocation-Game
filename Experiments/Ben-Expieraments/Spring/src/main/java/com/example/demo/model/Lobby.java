package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby 
{
	private List<Player> playerIds;
	
	public Lobby(List<Player> playerIds)
	{
		this.playerIds = playerIds;
	}
	
	public Lobby() {
		this.playerIds = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player) {
		this.playerIds.add(player);
	}
	
	public boolean removePlayer(Player player) {
		return this.playerIds.remove(player);
	}
	
	public List<Player> listPlayers() {
		return this.playerIds;
	}
}
