package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.demo.model.PlayerStats;

@Repository("fakeStatsDao")
public class FakePlayerStatsDataAcessService implements PlayerStatsDao
{
	private static List<PlayerStats> DB = new ArrayList<>();
	
	@Override
	public int insertPlayerStats(PlayerStats playerStats) {
		DB.add(new PlayerStats(playerStats));
		return 1;
	}
	
	@Override
	public Optional<PlayerStats> getPlayerStatsById(long id) {
		return DB.stream()
				.filter(playerStats -> playerStats.getPlayerId() == id)
				.findFirst();
	}
	
	@Override
	public int updatePlayerStatsById(long id, PlayerStats playerStats) {
		return getPlayerStatsById(id)
				.map(p -> {
					int indexOfPlayerStatsToDelete = DB.indexOf(playerStats);
					if (indexOfPlayerStatsToDelete >= 0) {
						DB.set(indexOfPlayerStatsToDelete, playerStats);
						return 1;
					}
					return 0;
				})
				.orElse(0);
	}
}
