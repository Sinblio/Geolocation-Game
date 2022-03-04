package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Player;


@Service
public interface PlayerDao 
{
	@Autowired
	int insertPlayer(Player player);
	
	List<Player> selectAllPlayers();
	
	Optional<Player> getPlayerById(long id);
	
	Optional<Player> getPlayerByName(String playerName);
	
	int deletePlayerById(long id);
	
	int updatePlayerById(long id, Player player);
}
