package ks4.campus_contagion.models;

import java.util.*;

import ks4.campus_contagion.dataquery.databaseEntities.*;


/**
 * This is the Math model.
 * @author Joseph Kueny
 */
public class LocalMath {

    private static double minLong = -93.654071;
    private static double maxLong = -93.641581;

    private static double minLat = 42.022847;
    private static double maxLat = 42.028827;

    private static double earthRadius = 6372.8;

    private static double userRadius = 10000.0;

    public static Double generateLatitude()
    {//Generates a random latitude within the confines of campus
        Random rand = new Random();

        double latitude = minLat + rand.nextDouble() * (maxLat - minLat);
        latitude = Double.parseDouble(String.format("%.6f", latitude));

        return latitude;
    }

    public static Double generateLongitude()
    {//Generates a random longitude within the confines of campus
        Random rand = new Random();

        double longitude = minLong + rand.nextDouble() * (maxLong - minLong);
        longitude = Double.parseDouble(String.format("%.6f", longitude));

        return longitude;
    }

    public static Entities objectsNearUser(User user, Entities inGame)          
	{//Takes in a user and all objects in a specific game and outputs the entities within userRadius
        Entities nearUser = new Entities();
        List<Powerup> powerups = new ArrayList<Powerup>();
        List<User> users = new ArrayList<User>();

        double dist;
        //If the object's location is within "25m" [now 10000] of the user, add to the nearUser Entity set

        for(int index = 0; index < inGame.getPowerups().size();index++)
        {//Check to see if any powerups are close
            dist = calcDist(user.getLatitude(), user.getLongitude(), inGame.getPowerups().get(index).getLatitude(), inGame.getPowerups().get(index).getLongitude());
            System.out.println(dist);
            if(dist <= userRadius){
                powerups.add(inGame.getPowerups().get(index));
                System.out.println(dist + "hi");}
        }

        for(int index = 0; index < inGame.getUsers().size();index++)
        {//Check to see if any users are close
            dist = calcDist(user.getLatitude(), user.getLongitude(), inGame.getUsers().get(index).getLatitude(), inGame.getUsers().get(index).getLongitude());
            if(dist <= userRadius)
                users.add(inGame.getUsers().get(index));
        }

        dist = calcDist(user.getLatitude(), user.getLongitude(), inGame.getZone().getLatitude(), inGame.getZone().getLongitude());
            if(dist <= userRadius) //Check to see if the extraction zone is near by
                nearUser.setEntraction(inGame.getZone());

        nearUser.setPowerupsList(powerups);
        nearUser.setUserList(users);
        
		return nearUser;
    }

    public static Lobby closestLobby(User user, List<Lobby> lobbies)
<<<<<<< HEAD
    {        
        //TODO                                   
=======
    {//Find the lobby closest to the calling user where lobby location is based off of host                       
>>>>>>> KuenyDemo5
        Lobby closestLobby = new Lobby();
        User hostUser = new User();

        Double shortDist = 1000000.00;
        Double newDist = 0.0;
        
        for(int index = 0;index < lobbies.size();index++)
        {//Look through all the lobbies and compare distances
            hostUser = lobbies.get(index).listUsers().get(0);
            newDist = calcDist(user.getLatitude(), user.getLongitude(), hostUser.getLatitude(), hostUser.getLongitude());
            if(newDist < shortDist)
            {
                closestLobby = lobbies.get(index);
                shortDist = newDist;
            }

        }
        return closestLobby;
    }
    
    /**
     * @param lat1 : Latitude of object 1
     * @param long1 : Longitude of object 1
     * @param lat2 : Latitude of object 2
     * @param long2 : Longitude of object 2
     * @return : The distance between the objects in meters
     */
    public static Double calcDist(Double lat1, Double long1, Double lat2, Double long2)
    { //The haversine formula in Java (https://rosettacode.org/wiki/Haversine_formula#Java)
      //The haversine formula (https://www.movable-type.co.uk/scripts/latlong.html) 

        double diffLat = Math.toRadians(lat2 - lat1);
        double diffLong = Math.toRadians(long2 - long1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        double a = Math.pow(Math.sin(diffLat / 2),2) + Math.pow(Math.sin(diffLong / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return earthRadius * c;
    }


}
