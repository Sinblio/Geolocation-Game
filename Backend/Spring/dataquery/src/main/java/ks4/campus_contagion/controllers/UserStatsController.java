package ks4.campus_contagion.controllers;

import ks4.campus_contagion.dataquery.databaseRepositories.*;
import ks4.campus_contagion.dataquery.databaseEntities.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/* USTATS CONTROLLER FEATURES
/allstats
	Returns all of the users' stats tables

/ustats/{uid}
	Returns a UID specified stats table
	Example: /ustats/4
*/
/**
 * This is the UserStats Controller. It is used to show the stats of all or one user.
 * @author Joseph Kueny
 */
@RestController
public class UserStatsController {

	@Autowired
	UserStatsTable ustats;

	
	/** 
	 * @return List<UserStats> : A list of all the users' stats.
	 */
	// Returns all of the users' stats data
	@RequestMapping("/allustats")
	List<UserStats> getAllUstats() {
		return ustats.findAll();
	}

	
	/** 
	 * @param uid : The UID of the specific user to get stats for.
	 * @return UserStats : The stats information about the specified user.
	 */
	//Returns one user's stats data specified by UID
	@RequestMapping("/ustats/{uid}")
	UserStats getUstats(@PathVariable Integer uid) {
		return ustats.findByUid(uid);
	}
	
	//Stats will be updated as the game progresses. They will not use RESTful API.

}


