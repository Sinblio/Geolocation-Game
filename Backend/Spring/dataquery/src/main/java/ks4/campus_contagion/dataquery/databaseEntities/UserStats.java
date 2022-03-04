package ks4.campus_contagion.dataquery.databaseEntities;

import javax.persistence.*;

/**
 * This is the UserStats entity. 
 * @author Joseph Kueny
 */
@Entity
@Table(name = "user_stats")
public class UserStats {
	
    @Id
    @Column(name = "uid", updatable = false, nullable = false)
	Integer uid;
	
	@Column
	Integer powerups_used;
	
	@Column
    String date_created;
    
    @Column
    String last_login;
    
    @Column
    Integer times_extracted;
    
    @Column
    Integer players_infected;
    
    @Column
	Integer games_played;
	
	
    public UserStats() 
	{

	}

	
    /** 
     * @param uid : The auto-generated, unique user ID.
     * @param powerups_used : The amount of powerups used by the entity's user.
     * @param last_login : The most recent login date for the entity's user.
     * @param times_extracted : The amount of times the entity's user has been extracted.
     * @param players_infected : The amount of other players that the entity's user has infected.
     * @param games_played : The amount of games played by the entity's user.
     */
    public UserStats(Integer uid, Integer powerups_used, String last_login, Integer times_extracted, Integer players_infected, Integer games_played) 
	{
		this.setUID(uid);
		this.setPowerupsUsed(powerups_used);
        this.setLastLogin(last_login);
        //No setDC because the SQL on the database takes care of it
        this.setTimesExtracted(times_extracted);
        this.setPlayersInfected(players_infected);
        this.setGamesPlayed(games_played);
	}
	
	
    /** 
     * @return Integer : The UID of the associated user to the entity.
     */
    public Integer getUID()
	{
		return uid;
	}	

	
    /** 
     * @param uid : The UID to be set to be associated to the entity.
     */
    public void setUID(Integer uid)
	{
		this.uid = uid;
	}

	
    /** 
     * @return Integer  : The number of powerups used by the entity's user.
     */
    public Integer getPowerupsUsed()
	{
		return powerups_used;
	}	

	
    /** 
     * @param powerups_used : The new number of powerups used by the entity's user.
     */
    public void setPowerupsUsed(Integer powerups_used)
	{
		this.powerups_used = powerups_used;
	}

	
    /** 
     * @return String : The date the entity's user was created.
     */
    public String getDateCreated()
	{
		return date_created;
    }	
    
    //No setDC because the SQL on the database takes care of it
    
    /** 
     * @return String : The last login of the entity's user.
     */
    public String getLastLogin()
    {
        return last_login;
    }

    
    /** 
     * @param last_login : The new date the entity's user logged in on.
     */
    public void setLastLogin(String last_login)
    {
        this.last_login = last_login;
    }

    
    /** 
     * @return Integer : The times the entity's user was extracted.
     */
    public Integer getTimesExtracted()
    {
        return times_extracted;
    }

    
    /** 
     * @param times_extracted : The new number of times the entity's user was extracted.
     */
    public void setTimesExtracted(Integer times_extracted)
    {
        this.times_extracted = times_extracted;
    }

    
    /** 
     * @return Integer : The number of other players that the entity's user infected.
     */
    public Integer getPlayersInfected()
    {
        return players_infected;
    }

    
    /** 
     * @param players_infected : The new number of players that the entity's user infected.
     */
    public void setPlayersInfected(Integer players_infected)
    {
        this.players_infected = players_infected;
    }

    
    /** 
     * @return Integer : The number of games played by the entity's user.
     */
    public Integer getGamesPlayed()
    {
        return games_played;
    }

    
    /** 
     * @param games_played : The new number of games played by the entity's user.
     */
    public void setGamesPlayed(Integer games_played)
    {
        this.games_played = games_played;
    }

	
    /** 
     * @return String : The UserStats entity as a String.
     */
    @Override
	public String toString() {
		return "UID: " + uid + "; Power-ups Used: " + powerups_used + "; Date Created: " + date_created + "; Last Login: " + last_login + "; Times Extracted: " + times_extracted + "; Players Infected: " + players_infected + "; Games Played: " + games_played;
	}
	
	
}