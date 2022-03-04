package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class PlayerStats 
{
	@Id
	private long playerID;
	private int gamesPlayed;
	private int timesExtracted;
	private int peopleInfected;
	private int powerUpsUsed;
	private double distanceTraveled;
	private int gamesWonAsPrime;
	private int mostInfectionsInAGame;
	
	public PlayerStats(long playerID)
	{
		this.playerID = playerID;
		this.setGamesPlayed(0);
		this.setTimesExtracted(0);
		this.setPeopleInfected(0);
		this.setPowerUpsUsed(0);
		this.setDistanceTraveled(0);
		this.setGamesWonAsPrime(0);
		this.setMostInfectionsInAGame(0);
	}
	
	public PlayerStats(@JsonProperty("id") long playerID, 
			@JsonProperty("gamesPlayed") int gamesPlayed,
			@JsonProperty("timesExtracted") int timesExtracted,
			@JsonProperty("peopleInfected") int peopleInfected,
			@JsonProperty("powerUpsUsed") int powerUpsUsed, 
			@JsonProperty("distanceTraveled") double distanceTraveled,
			@JsonProperty("gamesWonAsPrime") int  gamesWonAsPrime, 
			@JsonProperty("mostInfectionsInAGame") int mostInfectionsInAGame)
	{
		this.playerID = playerID;
		this.gamesPlayed = gamesPlayed;
		this.timesExtracted = timesExtracted;
		this.peopleInfected = peopleInfected;
		this.powerUpsUsed = powerUpsUsed;
		this.distanceTraveled = distanceTraveled;
		this.gamesWonAsPrime = gamesWonAsPrime;
		this.mostInfectionsInAGame = mostInfectionsInAGame;
	}
	
	public PlayerStats(PlayerStats playerStats)
	{
		this.playerID = playerStats.getPlayerId();
		this.setGamesPlayed(playerStats.getGamesPlayed());
		this.setTimesExtracted(playerStats.getTimesExtracted());
		this.setPeopleInfected(playerStats.getPeopleInfected());
		this.setPowerUpsUsed(playerStats.getPowerUpsUsed());
		this.setDistanceTraveled(playerStats.getDistanceTraveled());
		this.setGamesWonAsPrime(playerStats.getGamesWonAsPrime());
		this.setMostInfectionsInAGame(playerStats.getMostInfectionsInAGame());
	}

	public int getMostInfectionsInAGame() {
		return mostInfectionsInAGame;
	}

	public void setMostInfectionsInAGame(int mostInfectionsInAGame) {
		this.mostInfectionsInAGame = mostInfectionsInAGame;
	}

	public int getGamesWonAsPrime() {
		return gamesWonAsPrime;
	}

	public void setGamesWonAsPrime(int gamesWonAsPrime) {
		this.gamesWonAsPrime = gamesWonAsPrime;
	}

	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	public int getPowerUpsUsed() {
		return powerUpsUsed;
	}

	public void setPowerUpsUsed(int powerUpsUsed) {
		this.powerUpsUsed = powerUpsUsed;
	}

	public int getPeopleInfected() {
		return peopleInfected;
	}

	public void setPeopleInfected(int peopleInfected) {
		this.peopleInfected = peopleInfected;
	}

	public int getTimesExtracted() {
		return timesExtracted;
	}

	public void setTimesExtracted(int timesExtracted) {
		this.timesExtracted = timesExtracted;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public long getPlayerId() {
		return playerID;
	}
}
