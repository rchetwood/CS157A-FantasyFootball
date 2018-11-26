package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.AccountDAOException;
import Exceptions.LeagueDAOException;
import Models.League;

public class LeagueDAO {
	private final String CREATE_LEAGUE = "INSERT INTO League (Number_of_Teams, Draft_Date) "
			+ "VALUES (?, ?)";
	private final String RETRIEVE_ALL_LEAGUES = "SELECT * FROM League";
	private final String RETRIEVE_LEAGUE = "SELECT * FROM League WHERE leagueID=?";
	private final String UPDATE_LEAGUE = "UPDATE League SET Number_of_Teams=?, Draft_Date=? "
			+ "WHERE leagueID=?";
	private final String DELETE_LEAGUE = "DELETE FROM League WHERE LeagueID=?";
	
	public void create(League league) throws  LeagueDAOException {
		Connection conn = ConnectionFactory.getConnections();
		try {
			PreparedStatement ps = conn.prepareStatement(CREATE_LEAGUE);
			ps.setInt(1, league.getNumber_of_teams());
			ps.setDate(2, league.getDraft_date());
			System.out.println(ps.toString());
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new LeagueDAOException("Unable to create league.");
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

	public List<League> retrieveAll() throws LeagueDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<League> allLeagues = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_ALL_LEAGUES);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				League league = LeagueDAO.extractLeague(rs);
				allLeagues.add(league);
			}
			ps.close();
			return allLeagues;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new LeagueDAOException("Unable to create league.");
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

	public League retrieve(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(League obj) {
		// TODO Auto-generated method stub
		
	}

	public void delete(League league) throws LeagueDAOException {
		Connection conn = ConnectionFactory.getConnections();

		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_LEAGUE);
			ps.setInt(1, league.getLeagueID());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new LeagueDAOException("Unable to delete league.");
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
	
	public static League extractLeague(ResultSet rs) throws SQLException {
		League league = new League();
		
		league.setNumber_of_teams(rs.getInt("Number_of_Teams"));
		league.setDraft_date(rs.getDate("Draft_Date"));
		
		return league;
	}

}
