package Models;

public class DefensivePerformanceStatistics {
	private int Interceptions;
	private int Fumbles_Recovered;
	private int Sacks;
	private int Safeties;
	private int Blocked_Punts;
	private int Points;
	private int Defensive_Stats_ID;

	public int getInterceptions(){
		return Interceptions;
	}

	public void setInterceptions(int Interceptions){
		this.Interceptions=Interceptions;
	}

	public int getFumbles_recovered(){
		return Fumbles_Recovered;
	}

	public void setFumbles_recovered(int Fumbles_Recovered){
		this.Fumbles_Recovered=Fumbles_Recovered;
	}

	public int getSacks(){
		return Sacks;
	}

	public void setSacks(int Sacks){
		this.Sacks=Sacks;
	}

	public int getSafeties(){
		return Safeties;
	}

	public void setSafeties(int Safeties){
		this.Safeties=Safeties;
	}

	public int getBlocked_punts(){
		return Blocked_Punts;
	}

	public void setBlocked_punts(int Blocked_Punts){
		this.Blocked_Punts=Blocked_Punts;
	}

	public int getPoints(){
		return Points;
	}

	public void setPoints(int Points){
		this.Points=Points;
	}

	public int getDefensive_stats_id(){
		return Defensive_Stats_ID;
	}

	public void setDefensive_stats_id(int Defensive_Stats_ID){
		this.Defensive_Stats_ID=Defensive_Stats_ID;
	}
}