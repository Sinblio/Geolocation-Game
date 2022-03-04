package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Player;
import com.example.demo.model.PlayerStats;
import com.example.demo.service.PlayerService;
import com.example.demo.service.PlayerStatsService;

@RequestMapping("api/player")
@RestController
public class PlayerController 
{
	private final PlayerService playerService;
	private final PlayerStatsService playerStatsService;
	
	@Autowired
	public PlayerController(PlayerService playerService, PlayerStatsService playerStatsService) {
		this.playerService = playerService;
		this.playerStatsService = playerStatsService;
	}
	
	@PostMapping
	public void addPlayer(@RequestBody Player player)
	{
		Player p = new Player();
		p.setPlayerName(player.getPlayerName());
		p.setPassword(player.getPassword());
		playerService.addPlayer(p);
		PlayerStats ps = new PlayerStats(p.getID());
		playerStatsService.addPlayerStats(ps);
	}
	
	@GetMapping
	List<Player> getAllPlayers() {
		return playerService.getAllPeople();
	}
	
	@GetMapping(path = "/id/{id}")
	public Player getPlayerById(@PathVariable("id") long id)
	{
		return playerService.getPlayerById(id)
				.orElse(null);
	}
	
	@GetMapping(path = "/user/{name}")
	public Player getPlayerById(@PathVariable("name") String name)
	{
		return playerService.getPlayerByName(name);
	}
	
	@GetMapping(path = "/stats/{id}")
	public PlayerStats getPlayerStatsById(@PathVariable("id") long id)
	{
		return playerStatsService.getPlayerStatsById(id)
				.orElse(null);
	}
	
	@PutMapping(path = "/stats/{id}")
	public void updatePlayerStats(@PathVariable("id") long id, @RequestBody PlayerStats playerStats)
	{
		playerStatsService.updatePlayerStatsById(id, playerStats);
	}
}
