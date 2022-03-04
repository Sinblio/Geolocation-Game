package ks4.campus_contagion.controllers;

import ks4.campus_contagion.dataquery.databaseRepositories.*;
import ks4.campus_contagion.dataquery.databaseEntities.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/* USTATS CONTROLLER FEATURES
/allstats
	@RequestMapping
	Returns all of the users' stats tables

/ustats/{uid}
	@RequestMapping
	Returns a UID specified stats table
	Example: /ustats/4
*/
@RestController
public class UserStatsController {

	@Autowired
	UserStatsTable ustats;

	// Returns all of the users' stats data
	@RequestMapping("/allustats")
	List<UserStats> getAllUstats() {
		return ustats.findAll();
	}

	//Returns one user's stats data specified by UID
	@RequestMapping("/ustats/{uid}")
	String getUstats(@PathVariable Integer uid) {
		return ustats.findByUid(uid).toString();
	}
	
	//Stats will be updated as the game progresses. They will not use RESTful API.

}


