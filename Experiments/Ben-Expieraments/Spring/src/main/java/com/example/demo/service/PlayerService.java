package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayerDao;
import com.example.demo.model.Player;

@Service
public class PlayerService 
{
	private final PlayerDao playerDao;
	
	@Autowired
	public PlayerService(@Qualifier("playerDao") PlayerDao playerDao) {
		this.playerDao = playerDao;
	}
	
	public int addPlayer(Player player) {
		return playerDao.insertPlayer(player);
	}

	public List<Player> getAllPeople() {
		return playerDao.selectAllPlayers();
	}
	
	public Optional<Player> getPlayerById(long id)
	{
		return playerDao.getPlayerById(id);
	}
	
	public Player getPlayerByName(String playerName)
	{
		return playerDao.getPlayerByName(playerName)
				.orElse(null);
	}
}
