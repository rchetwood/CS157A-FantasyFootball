package Models;

public class Manager {
	private int managerID;
	private int Account_ID;
	private int League_ID;
	
	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public int getAccount_id(){
		return Account_ID;
	}

	public void setAccount_id(int Account_ID){
		this.Account_ID=Account_ID;
	}

	public int getLeague_id(){
		return League_ID;
	}

	public void setLeague_id(int League_ID){
		this.League_ID=League_ID;
	}
}