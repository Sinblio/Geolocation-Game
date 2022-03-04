package ks4.campus_contagion.models;

import java.util.List;

import ks4.campus_contagion.dataquery.databaseEntities.*;

/**
 * This is the Game model.
 * @author Joseph Kueny
 */
public class Game 
{
	private Entities objects;
	private Lobby lobby;
	private List<User> users;
	private Integer userCount;
	
	public Game()
	{
		
	}

	
	/** 
	 * @param lobby : The lobby the game is in
	 */
	public Game(Lobby lobby)
	{
		this.lobby = lobby;
		this.users = lobby.listUsers();
		this.userCount = users.size();
	}

	public boolean init()
	{//Initializes the game by calling loadEntities()
		try
		{
			loadEntities();
			return true;
		}catch(Exception e) 
		{
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	public List<User> getUsers()
	{
		return users;
	}
	
	/** 
	 * @param user : The user to find other objects near
	 * @return Entities : A list of entities that are close to the user
	 */
	public Entities objectsNearUser(User user)
	{//Calls the LocalMath function objectsNearUser()
		return LocalMath.objectsNearUser(user, objects);
	}

	private void loadEntities()
	{//Creates and saves randomly genereated powerups and an extraction zone.
	 //Saves the users from the lobby into the game
		List<Powerup> powerups= Powerup.generatePowerups(userCount * 3);

		ExtractionZone zone = ExtractionZone.generateZone();

		objects = new Entities(this.users, powerups, zone);
	}

	public Entities getObjects()
	{
		return objects;
	}

}
