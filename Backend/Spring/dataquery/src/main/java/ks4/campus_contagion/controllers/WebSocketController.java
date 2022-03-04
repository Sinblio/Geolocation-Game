package ks4.campus_contagion.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.*;
import javax.websocket.server.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ks4.campus_contagion.dataquery.databaseEntities.*;
import ks4.campus_contagion.dataquery.databaseRepositories.UsersTable;
import ks4.campus_contagion.localServices.*;
import ks4.campus_contagion.models.*;

@ServerEndpoint("/websocket/{uid}")
@Component
public class WebSocketController {

	private LobbyService lobbyService = new LobbyService();

	private static UsersTable users;

	//This is how to access the users table in the database
	@Autowired
	public void UserTable(UsersTable users) {
		this.users = users;
	}

	private User user;

	// Store all socket session and their corresponding uid
	private static Map<Session, Integer> sessionUIDMap = new HashMap<>();
	private static Map<Integer, Session> uidSessionMap = new HashMap<>();

	private final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

	//When
	@OnOpen
	public void onOpen(Session session, @PathParam("uid") Integer uid) throws IOException {
		logger.info("Entered into Open");

		this.user = users.findByUid(uid);

		sessionUIDMap.put(session, uid);
		uidSessionMap.put(uid, session);

		String message = "Connected to session: 0x" + session.getId();

		sendStringToParticularUser(uid, message);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		// Handle new messages
		logger.info("Entered into Message: Got Message: " + message);
		Integer uid = sessionUIDMap.get(session);

		if (message.startsWith("JoinLobby")) {// Adds user to a lobby, if lobby is -1 then the closest lobby will be
												// selected
			String[] input = message.split(",");
			Integer lobby = Integer.parseInt(input[1]);
			joinLobby(lobby, uid);
		}

		if (message.startsWith("UpdateLocation")) {// Updates the user's location within the game
			String[] input = message.split(",");

			Double latitude = Double.parseDouble(input[1]);
			Double longitude = Double.parseDouble(input[2]);
			updateLocationInGame(uid, latitude, longitude);
		}

		if (message.equals("StartGame")) {// Starts the game

			int lobbyNum = lobbyService.findUser(this.user);
			startGame(lobbyNum);
		}

	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		Integer uid = sessionUIDMap.get(session);
		sessionUIDMap.remove(session);
		uidSessionMap.remove(uid);

		leaveLobby(uid);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		logger.error("" + throwable);
	}

	private void sendStringToParticularUser(Integer uid, String message) {
		try {
			uidSessionMap.get(uid).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	private void sendEntitiesToParticularUser(Integer uid, Entities obj)
    {	
    	try {
			String jsonUsers = objectToJson(obj.getUsers());
			jsonUsers = jsonUsers.replace(']', ',');

			System.out.println(obj.getPowerups().get(0).toString());


			String jsonPowerups = objectToJson(obj.getPowerups());
			jsonPowerups = jsonPowerups.replace('[', ' ');
			jsonPowerups = jsonPowerups.replace(']', ',');

			String jsonZone = objectToJson(obj.getZone());
			jsonZone += ']';


    		uidSessionMap.get(uid).getBasicRemote().sendText(jsonUsers + jsonPowerups + jsonZone);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
    private static void broadcast(String message) throws IOException 
    {	  
    	sessionUIDMap.forEach((session, uid) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
    
    private void joinLobby(Integer lobbyNum, Integer uid) 
    {	 //Only have one game and one lobby [0]
		if(lobbyNum < 0){
			//TODO
			//Work on in LocalMath
			lobbyNum = lobbyService.showClosestLobby(this.user);
		}
			
		lobbyService.addUser(lobbyNum, this.user);
		
		List<User> inLobby = lobbyService.getUsers(lobbyNum);
		
		String message = "User joined: \"" + this.user.getUNAME() + "\"";
		for (User user : inLobby)
		{
			sendStringToParticularUser(user.getUID(), message);
		}
    }
    
    private void startGame(int lobbyNum) 
    {		
		List<User> inLobby = lobbyService.getUsers(lobbyNum);
		
		String message = "Game Starting";
		
		lobbyService.startGame(lobbyNum);
		
		for (User user : inLobby)
		{
			sendStringToParticularUser(user.getUID(), message);
		}
    }
    
    private void leaveLobby(Integer uid) throws IOException
    {      
		int lobbyNum = lobbyService.findUser(this.user);
	
		if(lobbyNum != -1)
		{
			lobbyService.removeUser(lobbyNum, this.user);
		
			List<User> inLobby = lobbyService.getUsers(lobbyNum);
			
			String message = "User \"" + this.user.getUNAME() + "\" left.";
			
			for (User user : inLobby)
			{
				sendStringToParticularUser(user.getUID(), message);
			}
		}
	}
	
	private void updateLocationInGame(Integer uid, Double latitude, Double longitude) throws IOException
	{
		// Integer userIndex = lobbyService.getGame().getUsers().indexOf(this.user);
		// User user = lobbyService.getGame().getUsers().get(userIndex);

		this.user.setLatitude(latitude);
		this.user.setLongitude(longitude);
		
		int lobbyNum = lobbyService.findUser(this.user);
		
		if (lobbyService.getLobby(lobbyNum).getState() == GameState.Game) {
			findNear(uid);
		}
	}

	private void findNear(Integer uid) throws IOException
	{
		Entities nearUser = lobbyService.getGame().objectsNearUser(this.user);
		sendEntitiesToParticularUser(uid, nearUser);
	}

	private String objectToJson(Object obj)
	{
		// Creating Object of ObjectMapper define in Jakson Api 
		ObjectMapper Obj = new ObjectMapper(); 

		try 
		{ 
			String jsonStr = Obj.writeValueAsString(obj); 
			return jsonStr;
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
			return null;
		} 
	}
}
