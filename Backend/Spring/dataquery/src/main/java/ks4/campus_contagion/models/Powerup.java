package ks4.campus_contagion.models;

import java.util.*;

/**
 * This is the Powerups model.
 * 
 * @author Joseph Kueny
 */
public class Powerup
{
    private Integer pid;
    private Double longitude;
    private Double latitude;
	
	private static Integer pidMax = 6;
	
	
    /** 
     * @param pid : The powerup ID.
     * @param longitude : The longitude the powerup is at.
     * @param latitude : The latitude the powerup is at.
     */
    public Powerup(Integer pid, Double longitude, Double latitude)
	{
		this.pid = pid;
        this.longitude = longitude;
        this.latitude = latitude;
    }

	public Integer getPid()
	{
		return this.pid;
	}

	public void setPid(Integer pid)
	{
		this.pid = pid;	
	}

	/**
	 * 
	 * @return Double : The longitude
	 */
	public Double getLongitude()
	{
			return this.longitude;
	}

	/**
	 * 
	 * @param longitude : The longitude
	 */
	public void setLongitude(Double longitude)
	{
			this.longitude = longitude;
	}

	/**
	 * 
	 * @return Double : The latitude
	 */
	public Double getLatitude()
	{
			return this.latitude;
	}

	/**
	 * 
	 * @param latitude : The latitude
	 */
	public void setLatitude(Double latitude)
	{
			this.latitude = latitude;
	}

	public static List<Powerup> generatePowerups(Integer numPow)
    {//Generates powerups
        List<Powerup> powerups = new ArrayList<Powerup>();
        Integer pid;

        if(numPow % 2 != 0) numPow++; //Make sure that numPow is even
        
        for(int i = 0;i < numPow;i++)
        {
            Random rand = new Random();

			//TODO
			//Fix this mess below
			//Supposed to make half even pid and half odd pid powerups [0 is even]
            pid = rand.nextInt(pidMax/2) * 2; //First half of powerups are even PID [All even PID are for seekers]
            if(i > numPow / 2) pid++; //Second half are odd PID [All odd PID are for hiders]

            powerups.add(new Powerup(pid, LocalMath.generateLatitude(), LocalMath.generateLongitude()));
		} 
		return powerups;
    }

}
