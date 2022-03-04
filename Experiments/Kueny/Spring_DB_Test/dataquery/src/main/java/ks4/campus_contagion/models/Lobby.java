package ks4.campus_contagion.models;

import java.util.ArrayList;
import java.util.List;

import ks4.campus_contagion.dataquery.databaseEntities.*;

public class Lobby 
{
	private List<User> userUids;
	
	public Lobby(List<User> userUids)
	{
		this.userUids = userUids;
	}
	
	public Lobby() {
		this.userUids = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.userUids.add(user);
	}
	
	public boolean removeUser(User user) {
		return this.userUids.remove(user);
	}
	
	public List<User> listUsers() {
		return this.userUids;
	}
}
