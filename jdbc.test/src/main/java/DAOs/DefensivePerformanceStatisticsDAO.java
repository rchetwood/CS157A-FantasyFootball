package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.DPSDAOException;
import Models.DefensivePerformanceStatistics;

public class DefensivePerformanceStatisticsDAO {

	private static final String RETRIEVE_ALL_D_STATS = 
			"SELECT * FROM DefensivePerformanceStatistics";
	private static final String RETRIEVE_D_STATS = 
			"SELECT * FROM DefensivePerformanceStatistics WHERE Defensive_Stats_ID=?";
	private static final String UPDATE_D_STATS = 
			"UPDATE DefensivePerformanceStatistics "
			+ "SET Interceptions=?, Fumbles_Recovered=?, Sacks=?, Safeties=?, Blocked_Punts=?, Points=? "
			+ "WHERE Defensive_Stats_ID=?";
	private static final String UPDATE_D_POINTS = 
			"UPDATE DefensivePerformanceStatistics "
			+ "SET Points=? WHERE Defensive_Stats_ID=?";
			
			
	public void create(DefensivePerformanceStatistics obj) {
		// TODO Auto-generated method stub
		
	}

	public List<DefensivePerformanceStatistics> retrieveAll() throws DPSDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<DefensivePerformanceStatistics> allDPS = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_ALL_D_STATS);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DefensivePerformanceStatistics dps = DefensivePerformanceStatisticsDAO.extractDPS(rs);
				allDPS.add(dps);
			}
			ps.close();
			return allDPS;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DPSDAOException("Unable to retrieve all DPS");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
				e.printStackTrace();
			}
		}
	}

	public DefensivePerformanceStatistics retrieve(int id) throws DPSDAOException {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_D_STATS);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				DefensivePerformanceStatistics dps = DefensivePerformanceStatisticsDAO.extractDPS(rs);
				ps.close();
				return dps;
			}
			else {
				ps.close();
				throw new DPSDAOException("DPS does not exist");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DPSDAOException("Unable to retrieve DPS");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
				e.printStackTrace();
			}
		}
	}

	public void update(DefensivePerformanceStatistics dps) throws DPSDAOException {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_D_STATS);
			ps.setInt(1, dps.getInterceptions());
			ps.setInt(2, dps.getFumbles_recovered());
			ps.setInt(3, dps.getSacks());
			ps.setInt(4, dps.getSafeties());
			ps.setInt(5, dps.getBlocked_punts());
			ps.setInt(6, dps.getPoints());
			ps.setInt(7, dps.getDefensive_stats_id());
			ps.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DPSDAOException("Unable to update DPS");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
				e.printStackTrace();
			}
		}
	}

	public void delete(DefensivePerformanceStatistics obj) {
		// TODO Auto-generated method stub
		
	}
	
	public static DefensivePerformanceStatistics extractDPS(ResultSet rs) throws SQLException {
		DefensivePerformanceStatistics dps = new DefensivePerformanceStatistics();
		
		dps.setInterceptions(rs.getInt("Interceptions"));
		dps.setFumbles_recovered(rs.getInt("Fumbles_Recovered"));
		dps.setSacks(rs.getInt("Sacks"));
		dps.setSafeties(rs.getInt("Safeties"));
		dps.setBlocked_punts(rs.getInt("Blocked_Punts"));
		dps.setPoints(rs.getInt("Points"));
		dps.setDefensive_stats_id(rs.getInt("Defensive_Stats_ID"));
		
		return dps;
		
	}
}
