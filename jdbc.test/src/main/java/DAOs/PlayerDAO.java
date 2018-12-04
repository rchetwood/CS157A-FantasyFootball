package DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import Models.Player;

public class PlayerDAO {

	public void create(Player obj) {
		// TODO Auto-generated method stub
		
	}

	public List<Player> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Player retrieve(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Player obj) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Player obj) {
		// TODO Auto-generated method stub
		
	}
	
	public static Player extractPlayer(ResultSet rs) throws SQLException {
		Player p = new Player();
		
		p.setPlayerID(rs.getInt("Player_ID"));
		p.setFirstName(rs.getString("FIrst_Name"));
		p.setLastName(rs.getString("Last_Name"));
		p.setPosition(rs.getString("Position"));
		p.setOffense(rs.getBoolean("Offense"));
		p.setOffensiveStatsID(rs.getInt("Offensive_Stats_ID"));
		p.setDefensiveStatsID(rs.getInt("Defensive_Stats_ID"));
		
		return p;
	}
}
