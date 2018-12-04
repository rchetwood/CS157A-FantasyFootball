package DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Exceptions.DPSDAOException;
import Exceptions.OPSDAOException;
import Models.DefensivePerformanceStatistics;
import Models.OffensivePerformanceStatistics;
import Models.Player;

public class PlayerDAO {
	
	public static int getPoints(int playerID) throws OPSDAOException, DPSDAOException {
		Connection conn = ConnectionFactory.getConnections();;
		
		try {
			OffensivePerformanceStatistics o = OffensivePerformanceStatisticsDAO.retrieve(playerID);
			DefensivePerformanceStatistics d = DefensivePerformanceStatisticsDAO.retrieve(playerID);
			
			return o.getPoints() + d.getPoints();
		}
		catch (OPSDAOException e) {
			e.printStackTrace();
			throw new OPSDAOException("Unable to get point total.");
		} catch (DPSDAOException e) {
			e.printStackTrace();
			throw new DPSDAOException("Unable to get point total.");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
				System.err.println("Unable to close connection.");
			}
		}
	}

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
