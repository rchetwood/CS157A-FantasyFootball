package Models;

public class OffensivePerformanceStatistics {
	private int Passing_Yards;
	private int Passing_TD;
	private int Passing_Int;
	private int Rushes;
	private int Rushing_Yards;
	private int Rushing_TD;
	private int Receiving_Receptions;
	private int Receiving_Yards;
	private int Receiving_TD;
	private int Receiving_Target;
	private int TwoPointConversion;
	private int Fumbles;
	private int misc_TD;
	private int Points;
	private int Offensive_Stats_ID;

	public int getPassing_yards(){
		return Passing_Yards;
	}

	public void setPassing_yards(int Passing_Yards){
		this.Passing_Yards=Passing_Yards;
	}

	public int getPassing_td(){
		return Passing_TD;
	}

	public void setPassing_td(int Passing_TD){
		this.Passing_TD=Passing_TD;
	}

	public int getPassing_int(){
		return Passing_Int;
	}

	public void setPassing_int(int Passing_Int){
		this.Passing_Int=Passing_Int;
	}

	public int getRushes(){
		return Rushes;
	}

	public void setRushes(int Rushes){
		this.Rushes=Rushes;
	}

	public int getRushing_yards(){
		return Rushing_Yards;
	}

	public void setRushing_yards(int Rushing_Yards){
		this.Rushing_Yards=Rushing_Yards;
	}

	public int getRushing_td(){
		return Rushing_TD;
	}

	public void setRushing_td(int Rushing_TD){
		this.Rushing_TD=Rushing_TD;
	}

	public int getReceiving_receptions(){
		return Receiving_Receptions;
	}

	public void setReceiving_receptions(int Receiving_Receptions){
		this.Receiving_Receptions=Receiving_Receptions;
	}

	public int getReceiving_yards(){
		return Receiving_Yards;
	}

	public void setReceiving_yards(int Receiving_Yards){
		this.Receiving_Yards=Receiving_Yards;
	}

	public int getReceiving_td(){
		return Receiving_TD;
	}

	public void setReceiving_td(int Receiving_TD){
		this.Receiving_TD=Receiving_TD;
	}

	public int getReceiving_target(){
		return Receiving_Target;
	}

	public void setReceiving_target(int Receiving_Target){
		this.Receiving_Target=Receiving_Target;
	}

	public int getTwoPointConversion(){
		return TwoPointConversion;
	}

	public void setTwoPointConversion(int TwoPointConversion){
		this.TwoPointConversion=TwoPointConversion;
	}

	public int getFumbles(){
		return Fumbles;
	}

	public void setFumbles(int Fumbles){
		this.Fumbles=Fumbles;
	}

	public int getMisc_td(){
		return misc_TD;
	}

	public void setMisc_td(int misc_TD){
		this.misc_TD=misc_TD;
	}

	public int getPoints(){
		return Points;
	}

	public void setPoints(int Points){
		this.Points=Points;
	}

	public int getOffensive_stats_id(){
		return Offensive_Stats_ID;
	}

	public void setOffensive_stats_id(int Offensive_Stats_ID){
		this.Offensive_Stats_ID=Offensive_Stats_ID;
	}
}