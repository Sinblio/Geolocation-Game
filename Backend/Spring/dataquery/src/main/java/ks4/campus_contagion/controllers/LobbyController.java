package ks4.campus_contagion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ks4.campus_contagion.dataquery.databaseEntities.*;
import ks4.campus_contagion.dataquery.databaseRepositories.*;
import ks4.campus_contagion.localServices.*;
import ks4.campus_contagion.models.LobbyInfo;

/**
 * This is the Lobby Controller. Used to show and edit players within a specific lobby.
 * @author Ben Schroeder
 */
@RequestMapping("lobby")
@RestController
public class LobbyController 
{
	@Autowired
	LobbyService lobby;
	
	@Autowired
	UsersTable users;
	
	@RequestMapping("/listplayers")
	public List<LobbyInfo> getPlayers()
	{
		return lobby.listLobbies();
	}
	
	/** 
	 * @return List : A list of users in the lobby.
	 */
	@RequestMapping("/listplayers/{lobby}")
	public List<User> getPlayersinLobby(@PathVariable("lobby") int lobbyNum)
	{
		return lobby.getUsers(lobbyNum);
	}
	
	
	/** 
	 * @param id : The user's UID to add to the lobby.
	 */
	@PostMapping("/add/{lobby}/{id}")
	public void addUser(@PathVariable("id") int id, @PathVariable("lobby") int lobbyNum)
	{
		User u = users.findById(id)
				.orElse(null);
		
		if (!u.equals(null))
			lobby.addUser(lobbyNum, u);
	}
	
	
	/** 
	 * @param id : The user's UID to remove from the lobby.
	 */
	@PostMapping("/remove/{lobby}/{id}")
	public void removeUser(@PathVariable("id") int id, @PathVariable("lobby") int lobbyNum)
	{
		User u = users.findById(id)
				.orElse(null);
		
		if (!u.equals(null))
			lobby.removeUser(lobbyNum, u);
	}
}
