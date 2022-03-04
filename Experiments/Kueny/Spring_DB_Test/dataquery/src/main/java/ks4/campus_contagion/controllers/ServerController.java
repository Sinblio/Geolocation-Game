package ks4.campus_contagion.controllers;

import org.springframework.web.bind.annotation.*;

/* SERVER CONTROLLER FEATURES
<no prompt>
	Returns "Please specify a request."

/serverstatus
	Returns "Contagion server is running."
*/
@RestController
public class ServerController {
	
	@RequestMapping()
	String noPrompt() {
		return "Please specify a request.";
	}

	@RequestMapping("/serverstatus")
	String sendStatus() {
		return "Contagion Server is running.";
	}

}


