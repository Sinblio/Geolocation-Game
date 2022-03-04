package ks4.campus_contagion.dataquery.databaseEntities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  	@Column(name = "uid", updatable = false, nullable = false)
	Integer uid;
	
	@Column
	String uname;
	
	@Column
	String password;

	@Column
	Integer active;

	public User() 
	{

	}

	public User(Integer uid, String uname, String password) 
	{
		this.setUID(uid);
		this.setUNAME(uname);
		this.setPassword(password);
		this.setActive(active);
	}
	
	public Integer getUID()
	{
		return uid;
	}	

	private void setUID(Integer uid)
	{
		this.uid = uid;
	}

	public String getUNAME()
	{
		return uname;
	}	

	public void setUNAME(String uname)
	{
		this.uname = uname;
	}

	public String getPassword()
	{
		return password;
	}	

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getActive()
	{
		return active;
	}	

	public void setActive(Integer active)
	{
		this.active = active;
	}

	@Override
	public String toString() {
		return "UID: " + uid + "; UNAME: " + uname + "; Password: " + password + "; Active: " + active;
	}

	
}