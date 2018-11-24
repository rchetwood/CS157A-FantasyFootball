package Models;

public class Player {
	private int playerID;
	private String firstName;
	private String lastName;
	private String position;
	private boolean isOffense;
	private int defensiveStatsID;
	private int offensiveStatsID;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public boolean isOffense() {
		return isOffense;
	}
	public void setOffense(boolean isOffense) {
		this.isOffense = isOffense;
	}
	public int getDefensiveStatsID() {
		return defensiveStatsID;
	}
	public void setDefensiveStatsID(int defensiveStatsID) {
		this.defensiveStatsID = defensiveStatsID;
	}
	public int getOffensiveStatsID() {
		return offensiveStatsID;
	}
	public void setOffensiveStatsID(int offensiveStatsID) {
		this.offensiveStatsID = offensiveStatsID;
	}
}