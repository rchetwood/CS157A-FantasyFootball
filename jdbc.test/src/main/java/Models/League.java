package Models;

public class League {
	private int leagueID;
	private int Number_of_Teams;
	private java.util.Date Draft_Date;

	public int getLeagueID() {
		return leagueID;
	}

	public void setLeagueID(int leagueID) {
		this.leagueID = leagueID;
	}

	public int getNumber_of_teams(){
		return Number_of_Teams;
	}

	public void setNumber_of_teams(int Number_of_Teams){
		this.Number_of_Teams=Number_of_Teams;
	}

	public java.util.Date getDraft_date(){
		return Draft_Date;
	}

	public void setDraft_date(java.util.Date Draft_Date){
		this.Draft_Date=Draft_Date;
	}
}