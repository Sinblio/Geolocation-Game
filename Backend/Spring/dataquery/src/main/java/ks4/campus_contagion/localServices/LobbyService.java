package ks4.campus_contagion.localServices;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


import ks4.campus_contagion.dataquery.databaseEntities.User;
import ks4.campus_contagion.models.*;

/**
 * This is the Lobby Service class. It controls which users are in what Lobby.
 * @author Ben Schroeder
 */
@Repository
public class LobbyService 
{
	private List<Lobby> lobbies;
	private Game game;
	
	/**
	 * Makes a dynamic list of lobbies.
	 */
	public LobbyService()
	{
		lobbies = new ArrayList<Lobby>();
		
		for (int j = 0; j < 1; j++)
			this.lobbies.add(new Lobby());
	}
	
	
	/** 
	 * @param lobbyNumber : The lobby of to start the game for.
	 */
	public Game startGame(int lobbyNumber)
	{
		lobbies.get(lobbyNumber).setState(GameState.Game);
		game = new Game(lobbies.get(lobbyNumber));

		if(game.init());
		else System.out.println("ERROR INITIALIZING GAME");

		return game;
	}
	
	
	/** 
	 * @param lobbyNumber : The lobby to add a user to.
	 * @param user : The user to be added to the desired lobby.
	 */
	public void addUser(int lobbyNumber, User user)
	{
		lobbies.get(lobbyNumber).addUser(user);
	}
	
	
	/** 
	 * @param lobbyNumber : The lobby to remove a user from.
	 * @param user : The user to remove from the lobby.
	 */
	public void removeUser(int lobbyNumber, User user)
	{
		lobbies.get(lobbyNumber).removeUser(user);
	}
	
	public Lobby getLobby(int lobbyNum) {
		return lobbies.get(lobbyNum);
	}
	
	/** 
	 * @param lobbyNumber : The lobby to list the users in.
	 * @return List : The list of users in the lobby.
	 */
	public List<User> getUsers(int lobbyNumber)
	{
		return lobbies.get(lobbyNumber).listUsers();
	}
	
	public Game getGame()
	{
		return this.game;
	}

	/** 
	 * @param user : The user to find
	 * @return Integer : The lobby a user is in.
	 */
	public int findUser(User user) 
	{
		int lobbyNum = -1;
		
		for (int j = 0; j < lobbies.size(); j++)
		{
			for (User k : lobbies.get(j).listUsers()) {
				if (k.getUNAME().equals(user.getUNAME()))
				{
					lobbyNum = j; 
				}
			}
		}
		
		return lobbyNum;
	}

	public Integer showClosestLobby(User user)
	{//Calls LocalMath method
		Lobby closestLobby = LocalMath.closestLobby(user, this.lobbies);
		return lobbies.indexOf(closestLobby);
	}
	
	public List<LobbyInfo> listLobbies() {
		List<LobbyInfo> list = new ArrayList<LobbyInfo>();
		
		for (Lobby l : lobbies) {
			list.add(new LobbyInfo(l));
		}
		
		return list;
	}

}
