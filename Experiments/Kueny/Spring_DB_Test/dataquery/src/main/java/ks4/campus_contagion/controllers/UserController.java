package ks4.campus_contagion.controllers;

import ks4.campus_contagion.dataquery.databaseRepositories.*;
import ks4.campus_contagion.dataquery.databaseEntities.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/* USER CONTROLLER FEATURES
/users
	@RequestMapping
	Returns a list of all the users in the database

/uid/{uid}
	@RequsestMapping	
	Returns one user based off of the specified UID
	Example: /uid/4

/uname/{uname}
	@RequestMapping
	Returns one user based off of the specified UNAME
	Example: /uname/Joseph

/createuser/?uname=<username>&pword=<password>
	@PostMapping
	Creates a new user with an auto-generated UID and uses the specified uname and password
	Returns the newly created user
	Example: /createuser/?uname=Timmy&pword=tempPass

/uid/{uid}/pword/{password}
	@PostMapping
	Updates the UID specified user's password with the given password
	Returns "Password Updated"
	Example: /uid/4/pword/newPass

/uname/{uname}/pword/{password}
	@PostMapping
	Updates the UNAME specified user's password with the given password
	Returns "Password Updated"
	Example: /uname/Joseph/pword/newPass

/uid/{uid}/deactivate
	@GetMapping
	Deactivates the UID specified user
	Returns the deactivated user
	Example: /uid/4/deactivate

/uname/{uname}/deactivate
	@GetMapping
	Deactivates the UNAME specified user
	Returns the deactivated user
	Example: /UNAME/Joseph/deactivate

/uid/{uid}/activate
	@GetMapping
	Activates the UID specified user
	Returns the activated user
	Example: /uid/4/activate

/uname/{uname}/activate
	@GetMapping
	Activates the UNAME specified user
	Returns the activated user
	Example: /UNAME/Joseph/activate
*/
@RestController
public class UserController {

	@Autowired
	UsersTable users;

	@Autowired
	UserStatsTable ustats;

	// Returns all of the users' data
	@RequestMapping("/users")
	List<User> getUsers() {
		return users.findAll();
	}

	/* #region User Selection */

	// Returns one user's data specified by UID
	@RequestMapping("/uid/{uid}")
	String getUsernameByUID(@PathVariable("uid") Integer uid) {
		return users.findByUid(uid).toString();
	}

	// Returns one user's data specified by UNAME
	@RequestMapping("/uname/{uname}")
	String getUsernameByUNAME(@PathVariable String uname) {
		return users.findByUname(uname).toString();
	}

	/* #endregion */

	// Creates a new user with an auto-generated unique UID and the given unique
	// name and password
	//
	//A linked user stats table is made along with the account
	@PostMapping("/createuser")
	String createUser(@RequestParam(value = "uname", required = true) String uname,
			@RequestParam(value = "pword", required = true) String password) {
		User user = new User();
		UserStats ustat = new UserStats();

		user.setUNAME(uname);
		user.setPassword(password);
		user.setActive(1);

		users.save(user);

		ustat.setUID(user.getUID());
		ustat.setPowerupsUsed(0);
		ustat.setTimesExtracted(0);
		ustat.setPlayersInfected(0);
		ustat.setGamesPlayed(0);

		ustats.save(ustat);

		return "User Created--> " + users.findByUname(uname).toString();
	}

	/* #region Password Update */

	// Update user password by UID
	@PostMapping("/uid/{uid}/pword/{password}")
	String updateUserPassByUID(@PathVariable("password") String password, @PathVariable("uid") Integer uid) {
		User user = users.getOne(uid);
		user.setPassword(password);
		users.save(user);
		return "Password Updated";
	}

	// Update password by UNAME
	@PostMapping("/uname/{uname}/pword/{password}")
	String updateUserPassByUNAME(@PathVariable("password") String password, @PathVariable("uname") String uname) {
		User user = users.findByUname(uname).get(0);
		user.setPassword(password);
		users.save(user);
		return "Password Updated";
	}

	/* #endregion */

	/* #region Deactivate User*/

	// Deactivate User by UID
	@GetMapping("/uid/{uid}/deactivate")
	String deactivateUserByUID(@PathVariable Integer uid) {
		User user = users.getOne(uid);
		user.setActive(0);
		users.save(user);
		return "User Deactivated--> " + user.toString();
	}

	// Deactivate User by UNAME
	@GetMapping("/uname/{uname}/deactivate")
	String deactivateUserByUNAME(@PathVariable String uname) {
		User user = users.findByUname(uname).get(0);
		user.setActive(0);
		users.save(user);
		return "User Deactivated--> " + user.toString();
	}

	/* #endregion */

	/* #region Activate User*/

	// Activate User by UID
	@GetMapping("/uid/{uid}/activate")
	String activateUserByUID(@PathVariable Integer uid) {
		User user = users.getOne(uid);
		user.setActive(1);
		users.save(user);
		return "User Activated--> " + user.toString();
	}

	// Activate User by UNAME
	@GetMapping("/uname/{uname}/activate")
	String activateUserByUNAME(@PathVariable String uname) {
		User user = users.findByUname(uname).get(0);
		user.setActive(1);
		users.save(user);
		return "User Activated--> " + user.toString();
	}

	/* #endregion */

}
