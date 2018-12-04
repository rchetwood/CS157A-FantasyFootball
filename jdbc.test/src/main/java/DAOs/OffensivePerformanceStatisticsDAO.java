package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.OPSDAOException;
import Models.OffensivePerformanceStatistics;

public class OffensivePerformanceStatisticsDAO {
	

	private static final String RETRIEVE_ALL_O_STATS = 
			"SELECT * FROM OffensivePerformanceStatistics";
	private static final String RETRIEVE_O_STATS = 
			"SELECT * FROM Offensive_Performance_Statistics WHERE Offensive_Stats_ID=?";
	private static final String UPDATE_O_STATS = 
			"UPDATE DefensivePerformanceStatistics "
			+ "SET Passing_Yards=?, Passing_TD=?, Passing_Int=?, "
			+ "Rushes=?, Rushing_Yards=?, Rushing_TD=?, "
			+ "Receiving_Receptions=?, Receiving_Yards=?, Receiving_TD=?, Receiving_Target=?, "
			+ "TwoPointConversion=?, Fumbles=?, misc_TD=?, Points=? "
			+ "WHERE Defensive_Stats_ID=?";
	private static final String UPDATE_O_POINTS = 
			"UPDATE OffensivePerformanceStatistics "
			+ "SET Points=? WHERE Offensive_Stats_ID=?";
			
			
	public void create(OffensivePerformanceStatistics obj) {
		// TODO Auto-generated method stub
		
	}

	public List<OffensivePerformanceStatistics> retrieveAll() throws OPSDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<OffensivePerformanceStatistics> allDPS = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_ALL_O_STATS);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OffensivePerformanceStatistics ops = OffensivePerformanceStatisticsDAO.extractOPS(rs);
				allDPS.add(ops);
			}
			ps.close();
			return allDPS;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new OPSDAOException("Unable to retrieve all OPS");
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

	public static OffensivePerformanceStatistics retrieve(int id) throws OPSDAOException {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_O_STATS);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				OffensivePerformanceStatistics ops = OffensivePerformanceStatisticsDAO.extractOPS(rs);
				ps.close();
				return ops;
			}
			else {
				ps.close();
				throw new OPSDAOException("OPS does not exist");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new OPSDAOException("Unable to retrieve OPS");
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

	public void update(OffensivePerformanceStatistics ops) throws OPSDAOException {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_O_STATS);
			ps.setInt(1, ops.getPassing_yards());
			ps.setInt(2, ops.getPassing_td());
			ps.setInt(3, ops.getPassing_int());
			ps.setInt(4, ops.getRushes());
			ps.setInt(5, ops.getRushing_yards());
			ps.setInt(6, ops.getRushing_td());
			ps.setInt(7, ops.getReceiving_receptions());
			ps.setInt(8, ops.getReceiving_yards());
			ps.setInt(9, ops.getReceiving_td());
			ps.setInt(10, ops.getReceiving_target());
			ps.setInt(11, ops.getTwoPointConversion());
			ps.setInt(12, ops.getFumbles());
			ps.setInt(13, ops.getMisc_td());
			ps.setInt(14, ops.getPoints());
			ps.setInt(15, ops.getOffensive_stats_id());
			ps.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new OPSDAOException("Unable to update OPS");
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

	public void delete(OffensivePerformanceStatistics obj) {
		// TODO Auto-generated method stub
		
	}
	
	public static OffensivePerformanceStatistics extractOPS(ResultSet rs) throws SQLException {
		OffensivePerformanceStatistics ops = new OffensivePerformanceStatistics();
		
		ops.setPassing_yards(rs.getInt("Passing_Yards"));
		ops.setPassing_td(rs.getInt("Passing_TD"));
		ops.setPassing_int(rs.getInt("Passing_Int"));
		ops.setRushes(rs.getInt("Rushes"));
		ops.setRushing_yards(rs.getInt("Rushing_Yards"));
		ops.setRushing_td(rs.getInt("Rushing_TD"));
		ops.setReceiving_receptions(rs.getInt("Receiving_Receptions"));
		ops.setReceiving_yards(rs.getInt("Receiving_Yards"));
		ops.setReceiving_td(rs.getInt("Receiving_TD"));
		ops.setReceiving_target(rs.getInt("Receiving_Target"));
		ops.setTwoPointConversion(rs.getInt("Two_point_conversion"));
		ops.setFumbles(rs.getInt("Fumbles"));
		ops.setMisc_td(rs.getInt("misc_TD"));
		ops.setPoints(rs.getInt("Points"));
		ops.setOffensive_stats_id(rs.getInt("Offensive_Stats_ID"));
		
		return ops;
		
	}
}
