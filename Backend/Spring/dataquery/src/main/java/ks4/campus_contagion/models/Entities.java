package ks4.campus_contagion.models;

import java.util.List;

import ks4.campus_contagion.dataquery.databaseEntities.*;

/**
 * This is the Entities model.
 * @author Joseph Kueny
 */
//Needed to keep a collection of all the entities in a game
public class Entities 
{
    private List<User> users;
    private List<Powerup> powerups;
    private ExtractionZone zone;
    

    public Entities()
    {
        
    }

	
    /** 
     * @param users : Users in the game.
     * @param powerups : Powerups for the game.
     * @param zone : Extraction zone for the game.
     */
    public Entities(List<User> users, List<Powerup> powerups, ExtractionZone zone)
	{
        this.users = users;
        this.powerups = powerups;
        this.zone = zone;
    }
    
    public Entities getAll()
    {
        return this;
    }


    /** 
     * @return List : The list of users in the game.
     */
    public List<User> getUsers()
	{
		return users;
    }	
    
    public List<Powerup> getPowerups()
    {
        return powerups;
    }

    public ExtractionZone getZone()
    {
        return zone;
    }

	
    /** 
     * @param user : Add a user to the game.
     */
    public void addUser(User user)
	{
		this.users.add(user);
    }

    public void removeUser(User user)
    {
        this.users.remove(user);
    }


    public void setPowerupsList(List<Powerup> powerups)
    {
        this.powerups = powerups;
    }

    /**
     * 
     * @param users : The users to add to the list
     */
    public void setUserList(List<User> users)
    {
        this.users = users;
    }

    public void setEntraction(ExtractionZone zone)
    {
        this.zone = zone;
    }
    
}
