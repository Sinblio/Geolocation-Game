package ks4.campus_contagion.dataquery;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import ks4.campus_contagion.dataquery.databaseEntities.User;
import ks4.campus_contagion.localServices.LobbyService;
import ks4.campus_contagion.models.Entities;
import ks4.campus_contagion.models.Game;
import ks4.campus_contagion.models.Lobby;

public class LobbyTest 
{
	LobbyService server;	
    User user1, user2, user3;
    List<User> ex;
   
    public static void main(String[] args)
    {
        LobbyTest tester = new LobbyTest();
        tester.setUp();
        System.out.println(tester.testLobbyFunctionality());
    }
   
    public void setUp()
    {
        server = mock(LobbyService.class);  

        //Create mock User objects
        user1 = mock(User.class);
        user2 = mock(User.class);
        user3 = mock(User.class);
        
        ex = new ArrayList<User>();
        
        ex.add(user1);
        ex.add(user2);
        ex.add(user3);
        
        ex.remove(user1);
        
        ex.add(user1);
    }

    public String testLobbyFunctionality()
    {
    	server.addUser(1, user1);
    	server.addUser(1, user2);
    	server.addUser(1, user3);
    	
    	server.removeUser(1, user1);
    	
    	when(server.showClosestLobby(user1)).thenReturn(1);	
    	
    	server.addUser(server.showClosestLobby(user1), user1);

        return (ex.equals(server.getUsers(1)))? "Pass":"Fail";
    }

}
