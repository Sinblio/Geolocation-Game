package ks4.campus_contagion.dataquery.databaseEntities;

import javax.persistence.*;

/**
 * This is the User entity. 
 * @author Joseph Kueny
 */
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  	@Column(name = "uid", updatable = false, nullable = false)
	private Integer uid;
	
	@Column
	private String uname;
	
	@Column
	private String password;

	@Column
	private Integer active;

	private Double longitude, latitude;


	public User() 
	{

	}

	
	/** 
	 * @param uid : The auto-generated, unique user ID.
	 * @param uname : A specified username.
	 * @param password : A specified password.
	 */
	public User(Integer uid, String uname, String password) 
	{
		this.setUID(uid);
		this.setUNAME(uname);
		this.setPassword(password);
		this.setActive(active);
	}
	
	
	/** 
	 * @return Integer : The UID of the entity.
	 */
	public Integer getUID()
	{
		return uid;
	}	

	
	/** 
	 * @param uid : The UID to set to the entity.
	 */
	private void setUID(Integer uid)
	{
		this.uid = uid;
	}

	
	/** 
	 * @return String : The UNAME of the entity.
	 */
	public String getUNAME()
	{
		return uname;
	}	

	
	/** 
	 * @param uname : The UNAME to set to the entity.
	 */
	public void setUNAME(String uname)
	{
		this.uname = uname;
	}

	
	/** 
	 * @return String : The Password of the entity.
	 */
	public String getPassword()
	{
		return password;
	}	

	
	/** 
	 * @param password : The Password to set to the Entity.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	
	/** 
	 * @return Integer : The status of the entity.
	 */
	public Integer getActive()
	{
		return active;
	}	

	
	/** 
	 * @param active : The status to set to the entity.S
	 */
	public void setActive(Integer active)
	{
		this.active = active;
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
	 * @return String : A User entity in String form.
	 */
	@Override
	public String toString() {
		return "UID: " + uid + "; UNAME: " + uname + "; Password: " + password + "; Active: " + active;
	}

	
}