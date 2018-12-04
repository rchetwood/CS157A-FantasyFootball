package Models;

public class League {
	private int leagueID;
	private String leagueName;
	private int Number_of_Teams;
	private java.sql.Date Draft_Date;
	
	public int getLeagueID() {
		return leagueID;
	}

	public void setLeagueID(int leagueID) {
		this.leagueID = leagueID;
	}
	
	public String getLeagueName() {
		return leagueName;
	}
	
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public int getNumber_of_teams(){
		return Number_of_Teams;
	}

	public void setNumber_of_teams(int Number_of_Teams){
		this.Number_of_Teams=Number_of_Teams;
	}

	public java.sql.Date getDraft_date(){
		return Draft_Date;
	}

	public void setDraft_date(java.sql.Date Draft_Date){
		this.Draft_Date=Draft_Date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Draft_Date == null) ? 0 : Draft_Date.hashCode());
		result = prime * result + Number_of_Teams;
		result = prime * result + leagueID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		League other = (League) obj;
		if (Draft_Date == null) {
			if (other.Draft_Date != null)
				return false;
		} else if (!Draft_Date.equals(other.Draft_Date))
			return false;
		if (Number_of_Teams != other.Number_of_Teams)
			return false;
		if (leagueID != other.leagueID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "League [leagueID=" + leagueID + ", Number_of_Teams=" + Number_of_Teams + ", Draft_Date=" + Draft_Date
				+ "name=" + leagueName + "]";
	}
	
}