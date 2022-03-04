package ks4.campus_contagion.models;

public class LobbyInfo 
{
	private int players;
	private GameState state;
	
	public LobbyInfo() {
		
	}
	
	public LobbyInfo(Lobby lobby) {
		this.players = lobby.listUsers().size();
		this.state = lobby.getState();
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
