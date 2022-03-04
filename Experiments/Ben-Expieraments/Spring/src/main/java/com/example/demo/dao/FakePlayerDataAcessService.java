package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Player;

@Repository("fakePLayerDao")
public class FakePlayerDataAcessService implements PlayerDao
{
	private static List<Player> DB = new ArrayList<>();
	
	@Override
	public int insertPlayer(Player player) {
		DB.add(new Player(player));
		return 1;
	}

	@Override
	public List<Player> selectAllPlayers() {
		return DB;
	}

	@Override
	public Optional<Player> getPlayerById(long id) {
		return DB.stream()
				.filter(player -> player.getID() == id)
				.findFirst();
	}

	@Override
	public int deletePlayerById(long id) {
		Optional<Player> maybePlayer = this.getPlayerById(id);
		if (!maybePlayer.isPresent())
			return 0;
		DB.remove(maybePlayer.get());
		return 1;
	}

	@Override
	public int updatePlayerById(long id, Player player) {
		return getPlayerById(id)
				.map(p -> {
					int indexOfPlayerToDelete = DB.indexOf(player);
					if (indexOfPlayerToDelete >= 0) {
						DB.set(indexOfPlayerToDelete, player);
						return 1;
					}
					return 0;
				})
				.orElse(0);
	}

	@Override
	public Optional<Player> getPlayerByName(String playerName) 
	{
		return DB.stream()
				.filter(player -> player.getPlayerName().equals(playerName))
				.findFirst();
	}

}
