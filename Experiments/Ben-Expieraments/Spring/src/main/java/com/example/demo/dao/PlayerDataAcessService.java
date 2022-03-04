package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Player;

@Repository("playerDao")
public class PlayerDataAcessService implements PlayerDao{

	@Autowired
	private static PlayerRepository playerRepository;
	
	@Override
	public int insertPlayer(Player player) {
		playerRepository.save(player);
		return 1;
	}

	@Override
	public List<Player> selectAllPlayers() {
		return playerRepository.findAll();
	}

	@Override
	public Optional<Player> getPlayerById(long id) {
		return playerRepository.findAll().stream()
				.filter(player -> player.getID() == id)
				.findFirst();
	}

	@Override
	public int deletePlayerById(long id) {
		playerRepository.deleteById(id);
		return 1;
	}

	@Override
	public int updatePlayerById(long id, Player player) {
		playerRepository.deleteById(id);
		playerRepository.save(player);
		return 1;
	}

	@Override
	public Optional<Player> getPlayerByName(String playerName) 
	{
		return playerRepository.findAll().stream()
				.filter(player -> player.getPlayerName().equals(playerName))
				.findFirst();
	}

}
