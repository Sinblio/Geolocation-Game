package com.example.demo.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.PlayerStats;

@Service
public interface PlayerStatsDao
{
	@Autowired
	public int insertPlayerStats(PlayerStats playerStats);

	int updatePlayerStatsById(long id, PlayerStats playerStats);

	Optional<PlayerStats> getPlayerStatsById(long id);
}
