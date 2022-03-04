package ks4.campus_contagion.models;

import ks4.campus_contagion.dataquery.databaseEntities.*;

import java.util.List;

public class Game 
{
	private List<User> userUids;
	
	public Game(List<User> userUids)
	{
		this.userUids = userUids;
	}
	
	public void findClosest(User user)
	{
		
	}
}
