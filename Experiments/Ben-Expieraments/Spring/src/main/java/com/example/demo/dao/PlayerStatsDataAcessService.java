package com.example.demo.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PlayerStats;

@Repository("statsDao")
public class PlayerStatsDataAcessService implements PlayerStatsDao
{

	@Autowired
	private PlayerStatsRepository playerStatsRepository;
	
	@Override
	public int insertPlayerStats(PlayerStats playerStats) {
		playerStatsRepository.save(playerStats);
		return 1;
	}

	@Override
	public int updatePlayerStatsById(long id, PlayerStats playerStats) {
		playerStatsRepository.delete(playerStatsRepository.findById(id).get());
		return insertPlayerStats(playerStats);
	}

	@Override
	public Optional<PlayerStats> getPlayerStatsById(long id) {
		return playerStatsRepository.findById(id);
	}

}
