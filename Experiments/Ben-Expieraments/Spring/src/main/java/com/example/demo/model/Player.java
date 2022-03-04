package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Player 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long playerID;
	private String playerName;
	private String password;
	
	public Player() 
	{
	}
	
	public Player(Player player)
	{
		this.playerName = player.getPlayerName();
		this.password = player.getPassword();
		this.playerID = player.getID();
	}
	
	public Player(@JsonProperty("playerName") String playerName,@JsonProperty("password") String password, @JsonProperty("id") long playerID)
	{
		this.playerName = playerName;
		this.password = password;
		this.playerID = playerID;
	}

	public long getID () 
	{
		return playerID;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
}
