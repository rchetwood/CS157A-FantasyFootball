package Models;

public class Manager {
	private int managerID;
	private String email;
	private int League_ID;
	
	
	@Override
	public String toString() {
		return "Manager [managerID=" + managerID + ", email=" + email + ", League_ID=" + League_ID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + League_ID;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + managerID;
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
		Manager other = (Manager) obj;
		if (League_ID != other.League_ID)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (managerID != other.managerID)
			return false;
		return true;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public int getLeague_id(){
		return League_ID;
	}

	public void setLeague_id(int League_ID){
		this.League_ID=League_ID;
	}
}