package ks4.campus_contagion.dataquery;

import ks4.campus_contagion.models.*;
import ks4.campus_contagion.dataquery.databaseEntities.*;
import ks4.campus_contagion.localServices.*;

import static org.mockito.Mockito.*;
/**
 * @author Joseph Kueny
 */
public class TestFindNearestLobby
{
	
    Game server;	
    LobbyService lobbyService;
    Lobby lobby;
    User user, hostUser;
   
    public static void main(String[] args)
    {
        TestFindNearestLobby tester = new TestFindNearestLobby();
        tester.setUp();
        System.out.println(tester.testClosestLobby());
    }
   
    private void setUp()
    {
        server = mock(Game.class); 
        lobbyService = mock(LobbyService.class);
        lobby = mock(Lobby.class);

        //Create mock User objects
        user = mock(User.class);
        hostUser = user;

        user.setLatitude(LocalMath.generateLatitude());
        user.setLongitude(LocalMath.generateLongitude());

        hostUser.setLatitude(LocalMath.generateLatitude());
        hostUser.setLongitude(LocalMath.generateLongitude());

        lobbyService.addUser(0, hostUser);
    }

    private String testClosestLobby()
    {      
        when(lobbyService.showClosestLobby(user)).thenReturn(0);		

        Integer lobby = lobbyService.showClosestLobby(user);

        lobbyService.addUser(lobby, user);

        return (lobby == 0) ? "Found the nearest lobby and added user.":"Incorrect lobby found.";
    }
}