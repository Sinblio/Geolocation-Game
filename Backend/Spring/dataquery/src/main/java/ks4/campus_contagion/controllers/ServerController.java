package ks4.campus_contagion.controllers;

import org.springframework.web.bind.annotation.*;

/* SERVER CONTROLLER FEATURES
<no prompt>
	Returns "Please specify a request."

/serverstatus
	Returns "Contagion server is running."
*/
/**
 * This is the Server Controller. Used to show the running status of the server.
 * @author Joseph Kueny
 */
@RestController
public class ServerController {
	
	
	/** 
	 * @return String : "Please specify a request."
	 */
	@RequestMapping()
	String noPrompt() {
		return "Please specify a request.";
	}

	
	/** 
	 * @return String : "Contagion Server is running."
	 */
	@RequestMapping("/serverstatus")
	String sendStatus() {
		return "Contagion Server is running.";
	}

}


