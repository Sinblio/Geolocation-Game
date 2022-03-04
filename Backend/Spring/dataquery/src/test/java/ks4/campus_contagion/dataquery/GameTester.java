package ks4.campus_contagion.dataquery;

import ks4.campus_contagion.models.*;
import ks4.campus_contagion.dataquery.databaseEntities.*;

import static org.mockito.Mockito.*;

public class GameTester 
{
	
    private Game server;	
    private User user, otherUser;   
    private Entities entities, otherEntities;
   
    public static void main(String[] args)
    {
        GameTester tester = new GameTester();
        tester.setUp();
        System.out.println(tester.testObjectsNear());
    }
   
    public void setUp()
    {
        //Create a mock Game object to be tested
        server = mock(Game.class);  

        //Create mock User objects
        user = mock(User.class);
        otherUser = user;
        
        //Create mock Entities objects
        entities = mock(Entities.class);
        otherEntities = entities;

        //Add the user to the entities classes [Not necessary for this to work]
        entities.addUser(user);
        //otherEntities.addUser(otherUser);
    }

    public String testObjectsNear()
    {      
        //mock the behavior of a Game object to return the entities near a user
        when(server.objectsNearUser(user)).thenReturn(entities);		

        return (otherEntities == server.objectsNearUser(user))? "Pass":"Fail";		
    }
}