package DAOs;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exceptions.AccountDAOException;
import Exceptions.LeagueDAOException;
import Models.Account;
import Models.League;

public class LeagueDAOTest {
	
	private final int TEST_NUMBER_OF_TEAMS1 = 42;
	private final java.sql.Date TEST_DRAFT_DATE1 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);
	
	private final int TEST_NUMBER_OF_TEAMS2 = 314;
	private final java.sql.Date TEST_DRAFT_DATE2 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);
	
	private final int TEST_NUMBER_OF_TEAMS3 = 217;
	private final java.sql.Date TEST_DRAFT_DATE3 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testCreate() throws SQLException  {
		Connection conn = ConnectionFactory.getConnections();
		try {
			// create new league to be inserted into db
			League league = new League();
			League resultLeague = null;
			
			// set params
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			
			// test function
			LeagueDAO.create(league);
			
			// prepare sql statement to get newly inserted league
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);
			
			// execute statement
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resultLeague = LeagueDAO.extractLeague(rs);
			}
			
			// test
			assertTrue(resultLeague.getDraft_date().toString()
					.equals(TEST_DRAFT_DATE1.toString())); 
			assertTrue(resultLeague.getNumber_of_teams() == TEST_NUMBER_OF_TEAMS1);
		}
		catch(LeagueDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				League resultLeague = new League();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					resultLeague = LeagueDAO.extractLeague(rs);
				}
				
				ps = conn.prepareStatement("DELETE "
						+ "FROM League "
						+ "WHERE League_ID=?");
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

	@Test
	public void testRetrieveAll() {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			ArrayList<League> allLeague1 = (ArrayList<League>) LeagueDAO.retrieveAll();
			
			// create leagues to be retrieved
			League league1 = new League();
			League league2 = new League();
			League league3 = new League();
			
			league1.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league1.setDraft_date(TEST_DRAFT_DATE1);
			
			league2.setNumber_of_teams(TEST_NUMBER_OF_TEAMS2);
			league2.setDraft_date(TEST_DRAFT_DATE2);
			
			league3.setNumber_of_teams(TEST_NUMBER_OF_TEAMS3);
			league3.setDraft_date(TEST_DRAFT_DATE3);
			
			// insert leagues
			LeagueDAO.create(league1);
			LeagueDAO.create(league2);
			LeagueDAO.create(league3);
			
			// get all leagues
			ArrayList<League> allLeague2 = (ArrayList<League>) LeagueDAO.retrieveAll();
			
			assertTrue(allLeague2.size() - allLeague1.size() == 3);
		}
		catch(LeagueDAOException e) {
			e.getStackTrace();
		}
		finally {
			try {
				League league1 = null;
				League league2 = null;
				League league3 = null;
				
				// get leagues
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);
				ResultSet rs1 = ps.executeQuery(); 
				if(rs1.next())
					league1 = LeagueDAO.extractLeague(rs1);
				
				// get leagues
				ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS2);
				ps.setDate(2, TEST_DRAFT_DATE2);
				ResultSet rs2 = ps.executeQuery();
				if(rs2.next())
					league2 = LeagueDAO.extractLeague(rs2);
				
				// get leagues
				ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS3);
				ps.setDate(2, TEST_DRAFT_DATE3);
				ResultSet rs3 = ps.executeQuery();
				if(rs3.next())
					league3 = LeagueDAO.extractLeague(rs3);
				
				LeagueDAO.delete(league1);
				LeagueDAO.delete(league2);
				LeagueDAO.delete(league3);
			} 
			catch (LeagueDAOException e) {
				System.err.println("Unable to delete test entry.");
				e.printStackTrace();
				fail();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	@Test
	public void testRetrieve() {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			League league = new League();
			League resultLeague = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			
			LeagueDAO.create(league);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date+?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				resultLeague = LeagueDAO.extractLeague(rs);
			assertTrue(resultLeague.getDraft_date().toString()
					.equals(league.getDraft_date().toString()));
		}
		catch(LeagueDAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				League league1 = null;
				
				// get leagues
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);
				ResultSet rs1 = ps.executeQuery(); 
				if(rs1.next())
					league1 = LeagueDAO.extractLeague(rs1);
				
				LeagueDAO.delete(league1);
			} 
			catch (LeagueDAOException e) {
				System.err.println("Unable to delete test entry.");
				e.printStackTrace();
				fail();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	@Test
	public void testUpdate() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			League league = new League();
			League updatedLeague = new League();
			
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			
			LeagueDAO.create(league);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				league = LeagueDAO.extractLeague(rs);
			
			updatedLeague.setDraft_date(league.getDraft_date());
			updatedLeague.setLeagueID(league.getLeagueID());
			updatedLeague.setNumber_of_teams(6000);
			
			LeagueDAO.update(updatedLeague);
			
			league = LeagueDAO.retrieve(updatedLeague.getLeagueID());
			
			assertTrue(league.getNumber_of_teams() == 6000);
		}
		catch(LeagueDAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			League league = new League();
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, 6000);
				ps.setDate(2, TEST_DRAFT_DATE1);
				ResultSet rs = ps.executeQuery();
				if(rs.next())
					league = LeagueDAO.extractLeague(rs);
				LeagueDAO.delete(league);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	@Test
	public void testDelete() throws SQLException, LeagueDAOException {
		Connection conn = ConnectionFactory.getConnections();
		try {
			// create league to be inserted db
			League league = new League();
			League resultLeague = new League();
			
			// set values
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);

			// create the league in sb
			LeagueDAO.create(league);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resultLeague = LeagueDAO.extractLeague(rs);
			}
			else {
				fail();
			}
			
			
			ArrayList<League> allLeague1 = (ArrayList<League>) LeagueDAO.retrieveAll();
			LeagueDAO.delete(resultLeague);
			ArrayList<League> allLeague2 = (ArrayList<League>) LeagueDAO.retrieveAll();
			

			assertTrue(allLeague1.size() - allLeague2.size() == 1);
		}
		catch(LeagueDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				League resultLeague = new League();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);
				
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
