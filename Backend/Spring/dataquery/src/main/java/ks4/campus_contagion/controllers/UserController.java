package ks4.campus_contagion.controllers;

import ks4.campus_contagion.dataquery.databaseRepositories.*;
import ks4.campus_contagion.dataquery.databaseEntities.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/* USER CONTROLLER FEATURES
/users
	Returns a list of all the users in the database

/uid/{uid}
	Returns one user based off of the specified UID
	Example: /uid/4

/uname/{uname}
	Returns one user based off of the specified UNAME
	Example: /uname/Joseph

/createuser/?uname=<username>&pword=<password>
	Creates a new user with an auto-generated UID and uses the specified uname and password
	Returns the newly created user
	Example: /createuser/?uname=Timmy&pword=tempPass

/uid/{uid}/pword/{password}
	Updates the UID specified user's password with the given password
	Returns "Password Updated"
	Example: /uid/4/pword/newPass

/uname/{uname}/pword/{password}
	Updates the UNAME specified user's password with the given password
	Returns "Password Updated"
	Example: /uname/Joseph/pword/newPass

/uid/{uid}/deactivate
	Deactivates the UID specified user
	Returns the deactivated user
	Example: /uid/4/deactivate

/uname/{uname}/deactivate
	Deactivates the UNAME specified user
	Returns the deactivated user
	Example: /UNAME/Joseph/deactivate

/uid/{uid}/activate
	Activates the UID specified user
	Returns the activated user
	Example: /uid/4/activate

/uname/{uname}/activate
	Activates the UNAME specified user
	Returns the activated user
	Example: /UNAME/Joseph/activate
*/
/**
 * This is the User Controller. This is used to see all users, specific users, create users, and edit users.
 * @author Joseph Kueny
 */
@RestController
public class UserController {

	@Autowired
	UsersTable users;

	@Autowired
	UserStatsTable ustats;
	
	/** 
	 * @return List<User> : Returns all of the users' data in the database.
	 */
	// Returns all of the users' data in the database
	@RequestMapping("/users")
	List<User> getUsers() {
		return users.findAll();
	}

	/* #region User Selection */

	/** 
	 * @param uid : The specific user's UID to return data about.
	 * @return User : Returns one user's data specified by UID.
	 */

	// Returns one user's data specified by UID
	@RequestMapping("/uid/{uid}")
	User getUsernameByUID(@PathVariable("uid") Integer uid) {
		return users.findByUid(uid);
	}

	
	/** 
	 * @param uname : The specific user's UNAME to return data about.
	 * @return User : Returns one user's data specified by UNAME.
	 */
	// Returns one user's data specified by UNAME
	@RequestMapping("/uname/{uname}")
	User getUsernameByUNAME(@PathVariable String uname) {
		return users.findByUname(uname);
	}

	/* #endregion */

	
	/** 
	 * @param uname : The UNAME for the newly created user.
	 * @param password : The password for the newly created user.
	 * @return User : Returns the database entry for the newly created user.
	 */

	// Creates a new user with an auto-generated unique UID and the given unique
	// name and password
	//
	//A linked user stats table is made along with the account
	@RequestMapping("/createuser")
	User createUser(@RequestParam(value = "uname", required = true) String uname,
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

		return users.findByUname(uname);
	}

	/* #region Password Update */

	/** 
	 * @param password : The new password for the user.
	 * @param uid : The UID of the user that needs edited.
	 * @return String : "Password Updated"
	 */

	// Update user password by UID
	@RequestMapping("/uid/{uid}/pword/{password}")
	String updateUserPassByUID(@PathVariable("password") String password, @PathVariable("uid") Integer uid) {
		User user = users.getOne(uid);
		user.setPassword(password);
		users.save(user);
		return "Password Updated";
	}

	
	/** 
	 * @param password : The new password for the user.
	 * @param uid : The UNAME of the user that needs edited.
	 * @return String : "Password Updated"
	 */
	// Update password by UNAME
	@RequestMapping("/uname/{uname}/pword/{password}")
	String updateUserPassByUNAME(@PathVariable("password") String password, @PathVariable("uname") String uname) {
		User user = users.findByUname(uname);
		user.setPassword(password);
		users.save(user);
		return "Password Updated";
	}

	/* #endregion */


	/* #region Deactivate User*/

	/** 
	 * @param uid : The UID of the user to deactivate.
	 * @return User : The user that was deactivated.
	 */
	// Deactivate User by UID
	@RequestMapping("/uid/{uid}/deactivate")
	User deactivateUserByUID(@PathVariable Integer uid) {
		User user = users.getOne(uid);
		user.setActive(0);
		users.save(user);
		return user;
	}

	
	/** 
	 * @param uname : The UNAME of the user to deactivate.
	 * @return User : The user that was deactivated.
	 */
	// Deactivate User by UNAME
	@RequestMapping("/uname/{uname}/deactivate")
	User deactivateUserByUNAME(@PathVariable String uname) {
		User user = users.findByUname(uname);
		user.setActive(0);
		users.save(user);
		return user;
	}

	/* #endregion */


	/* #region Activate User*/

	/** 
	 * @param uid : The UID of the user to activate.
	 * @return User : The user that was activated.
	 */
	// Activate User by UID
	@RequestMapping("/uid/{uid}/activate")
	User activateUserByUID(@PathVariable Integer uid) {
		User user = users.getOne(uid);
		user.setActive(1);
		users.save(user);
		return user;
	}

	
	/** 
	 * @param uname : The UNAME of the user to activate.
	 * @return User : The user that was activated.
	 */
	// Activate User by UNAME
	@RequestMapping("/uname/{uname}/activate")
	User activateUserByUNAME(@PathVariable String uname) {
		User user = users.findByUname(uname);
		user.setActive(1);
		users.save(user);
		return user;
	}

	/* #endregion */

}
