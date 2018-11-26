package DAOs;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exceptions.AccountDAOException;
import Exceptions.LeagueDAOException;
import Models.Account;
import Models.League;

public class LeagueDAOTest {
	
	private final int TEST_NUMBER_OF_TEAMS = 3;
	private final java.sql.Date TEST_DRAFT_DATE = new java.sql.Date(new java.util.Date().getTime());
	
	private LeagueDAO leagueDAO;

	@Before
	public void setUp() {
		leagueDAO = new LeagueDAO();
	}

	@After
	public void tearDown() {
		leagueDAO = null;
		assertNull(leagueDAO);
	}

	@Test
	public void testCreate() throws SQLException  {
		Connection conn = ConnectionFactory.getConnections();
		try {
			// create new league to be inserted into db
			League league = new League();
			League resultLeague = null;
			
			// set params
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS);
			league.setDraft_date(TEST_DRAFT_DATE);
			
			// test function
			leagueDAO.create(league);
			
			// prepare sql statement to get newly inserted league
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS);
			ps.setDate(2, TEST_DRAFT_DATE);
			
			// execute statement
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resultLeague = LeagueDAO.extractLeague(rs);
			}
			
			assert(resultLeague.getDraft_date().equals(TEST_DRAFT_DATE) 
					&& resultLeague.getNumber_of_teams() == TEST_NUMBER_OF_TEAMS);
		}
		catch(LeagueDAOException e) {
			
		}
	}

	@Test
	public void testRetrieveAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetrieve() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() throws SQLException, LeagueDAOException {
		Connection conn = ConnectionFactory.getConnections();
		try {
			League league = new League();
			League resultLeague = new League();
			
			league.setLeagueID(TEST_NUMBER_OF_TEAMS);
			league.setDraft_date(TEST_DRAFT_DATE);

			leagueDAO.create(league);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS);
			ps.setDate(2, TEST_DRAFT_DATE);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resultLeague = LeagueDAO.extractLeague(rs);
			}
			else {
				fail();
			}
			
			leagueDAO.delete(resultLeague);
			
			ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS);
			ps.setDate(2, TEST_DRAFT_DATE);
			
			 rs = ps.executeQuery();
			
			assertTrue(!rs.next());
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				League resultLeague = new League();;
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS);
				ps.setDate(2, TEST_DRAFT_DATE);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					resultLeague = LeagueDAO.extractLeague(rs);
				}
				
				ps = conn.prepareStatement("DELETE "
						+ "FROM League "
						+ "WHERE League_ID+?");
				ps.setInt(1, resultLeague.getLeagueID());
				ps.executeUpdate();
				ps.close();
			} 
			catch (SQLException e) {
				System.err.println("Unable to delete test entry.");
				e.printStackTrace();
				fail();
			}
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
				e.printStackTrace();
			}
		}
	}

}
