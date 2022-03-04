package com.example.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayerStatsDao;
import com.example.demo.model.Player;
import com.example.demo.model.PlayerStats;

@Service
public class PlayerStatsService 
{
	private final PlayerStatsDao playerStatsDao;
	
	@Autowired
	public PlayerStatsService(@Qualifier("statsDao") PlayerStatsDao playerDao) {
		this.playerStatsDao = playerDao;
	}
	
	public int addPlayerStats(PlayerStats playerStats) {
		return playerStatsDao.insertPlayerStats(playerStats);
	}
	
	public int updatePlayerStatsById(long id, PlayerStats playerStats)
	{
		return playerStatsDao.updatePlayerStatsById(id, playerStats);
	}
	
	public Optional<PlayerStats> getPlayerStatsById(long id)
	{
		return playerStatsDao.getPlayerStatsById(id);
	}
}
