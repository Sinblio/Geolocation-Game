package ks4.campus_contagion.dataquery.databaseEntities;

import javax.persistence.*;

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

	public UserStats(Integer uid, String uname, String password) 
	{
		this.setUID(uid);
		this.setPowerupsUsed(powerups_used);
        this.setLastLogin(last_login);
        //No setDC because the SQL on the database takes care of it
        this.setTimesExtracted(times_extracted);
        this.setPlayersInfected(players_infected);
        this.setGamesPlayed(games_played);
	}
	
	public Integer getUID()
	{
		return uid;
	}	

	public void setUID(Integer uid)
	{
		this.uid = uid;
	}

	public Integer getPowerupsUsed()
	{
		return powerups_used;
	}	

	public void setPowerupsUsed(Integer powerups_used)
	{
		this.powerups_used = powerups_used;
	}

	public String getDateCreated()
	{
		return date_created;
	}	

    //No setDC because the SQL on the database takes care of it

    public String getLastLogin()
    {
        return last_login;
    }

    public void setLastLogin(String last_login)
    {
        this.last_login = last_login;
    }

    public Integer getTimesExtracted()
    {
        return times_extracted;
    }

    public void setTimesExtracted(Integer times_extracted)
    {
        this.times_extracted = times_extracted;
    }

    public Integer getPlayersInfected()
    {
        return players_infected;
    }

    public void setPlayersInfected(Integer players_infected)
    {
        this.players_infected = players_infected;
    }

    public Integer getGamesPlayed()
    {
        return games_played;
    }

    public void setGamesPlayed(Integer games_played)
    {
        this.games_played = games_played;
    }

	@Override
	public String toString() {
		return "UID: " + uid + "; Power-ups Used: " + powerups_used + "; Date Created: " + date_created + "; Last Login: " + last_login + "; Times Extracted: " + times_extracted + "; Players Infected: " + players_infected + "; Games Played: " + games_played;
	}
	
	
}