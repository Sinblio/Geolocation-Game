package ks4.campus_contagion.models;

import java.util.ArrayList;
import java.util.List;

import ks4.campus_contagion.dataquery.databaseEntities.*;
 

/**
 * This is the Lobby model.
 * @author Ben Schroeder
 */
public class Lobby 
{
	private List<User> users;
	private GameState state;
	
	/** 
	 * @param userUids : The user UIDs of all users in the lobby.
	 */
	public Lobby(List<User> userUids)
	{
		this.users = userUids;
		this.state = GameState.Lobby;
	}
	
	
	/** 
	 * A new lobby object.
	 */
	public Lobby() {
		this.users = new ArrayList<User>();
	}
	
	
	/** 
	 * @param user : The user to add to the lobby.
	 */
	public void addUser(User user) {
		this.users.add(user);
	}
	
	
	/** 
	 * @param user : The user to remove from the lobby.
	 * @return boolean : If the removal succeeded or not.
	 */
	public boolean removeUser(User user) {
		return this.users.remove(user);
	}
	
	
	/** 
	 * @return List : All users for the lobby.
	 */
	public List<User> listUsers() {
		return this.users;
	}


	public GameState getState() {
		return state;
	}


	public void setState(GameState state) {
		this.state = state;
	}
}
