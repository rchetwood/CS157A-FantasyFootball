package DAOs;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import Exceptions.AccountDAOException;
import Exceptions.LeagueDAOException;
import Exceptions.ManagerDAOException;
import Exceptions.RosterDAOException;
import Models.Account;
import Models.League;
import Models.Manager;
import Models.Player;
import Models.Roster;

public class RosterDAOTest {
	
	private final String TEST_ACCOUNT_EMAIL1 = "TEST";
	private final String TEST_FIRST_NAME1 = "TEST";
	private final String TEST_LAST_NAME1 = "TEST";
	private final String TEST_PASSWORD1 = "TEST";
	
	private final int TEST_NUMBER_OF_TEAMS1 = 42;
	private final java.sql.Date TEST_DRAFT_DATE1 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);
	private final String TEST_NAME1 = "TEST NAME 1";
	private final int TEST_ID1 = new Random().nextInt(2147483647);

	@Test
	public void testAvailablePlayers() {
		Connection conn =  ConnectionFactory.getConnections();
		try {
			
			// create account, league, and manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			AccountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			league.setLeagueID(TEST_ID1);
			league.setLeagueName(TEST_NAME1);
			LeagueDAO.create(league);
			
			Manager manager = new Manager();
			manager.setEmail(TEST_ACCOUNT_EMAIL1);
			manager.setLeague_id(TEST_ID1);
			ManagerDAO.create(manager);
			
			int managerID = ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID();
			
			RosterDAO.addPlayer(managerID, 1);
			ArrayList<Player> availablePlayers = (ArrayList<Player>) RosterDAO.availablePlayers(managerID);
			
			for(Player p : availablePlayers) {
				if(p.getPlayerID() == 1) {
					fail();
				}
			}
		}
		catch (ManagerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RosterDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeagueDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 1);
				ps.executeUpdate();
				
				ManagerDAO.delete(ManagerDAO.retrieveAnAccountsManagers
				(TEST_ACCOUNT_EMAIL1).get(0));
				
				LeagueDAO.delete(LeagueDAO.retrieve(TEST_ID1));
				
				AccountDAO.delete(TEST_ACCOUNT_EMAIL1);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ManagerDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}
	
	@Test
	public void testAddPlayer() {
		Connection conn =  ConnectionFactory.getConnections();
		try {
			
			// create account, league, and manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			AccountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			league.setLeagueID(TEST_ID1);
			league.setLeagueName(TEST_NAME1);
			LeagueDAO.create(league);
			
			Manager manager = new Manager();
			manager.setEmail(TEST_ACCOUNT_EMAIL1);
			manager.setLeague_id(TEST_ID1);
			ManagerDAO.create(manager);
			
			RosterDAO.addPlayer(ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID(), 1);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Roster "
					+ "WHERE Manager_ID=? AND Player_ID=?");
			ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
			ps.setInt(2, 1);
			ResultSet rs = ps.executeQuery();
			Roster r = null;
			if(rs.next()) {
				r = RosterDAO.extractRoster(rs);
			}
			
			assertTrue(r.getManagerID() == ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
			assertTrue(r.getPlayerID() == 1);
			
		}
		catch(RosterDAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeagueDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 1);
				ps.executeUpdate();
				
				ManagerDAO.delete(ManagerDAO.retrieveAnAccountsManagers
				(TEST_ACCOUNT_EMAIL1).get(0));
				
				LeagueDAO.delete(LeagueDAO.retrieve(TEST_ID1));
				
				AccountDAO.delete(TEST_ACCOUNT_EMAIL1);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ManagerDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}

	@Test
	public void testDeletePlayer() {
		Connection conn =  ConnectionFactory.getConnections();
		try {
			
			// create account, league, and manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			AccountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			league.setLeagueID(TEST_ID1);
			league.setLeagueName(TEST_NAME1);
			LeagueDAO.create(league);
			
			Manager manager = new Manager();
			manager.setEmail(TEST_ACCOUNT_EMAIL1);
			manager.setLeague_id(TEST_ID1);
			ManagerDAO.create(manager);
			
			int managerID = ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID();
			
			RosterDAO.addPlayer(managerID, 1);
			
			RosterDAO.deletePlayer(managerID, 1);
			try {
				Player p = RosterDAO.retrievePlayer(managerID, 1);
			}
			catch(RosterDAOException e) {
				assertTrue(e.getMessage().equals("Player is not on roster."));
			}
			
		}
		catch(RosterDAOException e) {
			e.printStackTrace();
		} 
		catch (LeagueDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 1);
				ps.executeUpdate();
				
				ManagerDAO.delete(ManagerDAO.retrieveAnAccountsManagers
				(TEST_ACCOUNT_EMAIL1).get(0));
				
				LeagueDAO.delete(LeagueDAO.retrieve(TEST_ID1));
				
				AccountDAO.delete(TEST_ACCOUNT_EMAIL1);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ManagerDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}

	@Test
	public void testRetrieveManagersRoster() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			// create account, league, and manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			AccountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			league.setLeagueID(TEST_ID1);
			league.setLeagueName(TEST_NAME1);
			LeagueDAO.create(league);
			
			Manager manager = new Manager();
			manager.setEmail(TEST_ACCOUNT_EMAIL1);
			manager.setLeague_id(TEST_ID1);
			ManagerDAO.create(manager);
			
			int managerID = ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID();
			
			RosterDAO.addPlayer(managerID, 1);
			RosterDAO.addPlayer(managerID, 2);
			RosterDAO.addPlayer(managerID, 3);
			
			ArrayList<Player> roster = (ArrayList<Player>) RosterDAO.retrieveManagersRoster(managerID);
			assertTrue(roster.size() == 3);
		}
		catch (LeagueDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RosterDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 1);
				ps.executeUpdate();
				
				ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 2);
				ps.executeUpdate();
				
				ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 3);
				ps.executeUpdate();
				
				
				
				ManagerDAO.delete(ManagerDAO.retrieveAnAccountsManagers
				(TEST_ACCOUNT_EMAIL1).get(0));
				
				LeagueDAO.delete(LeagueDAO.retrieve(TEST_ID1));
				
				AccountDAO.delete(TEST_ACCOUNT_EMAIL1);
				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ManagerDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}

	@Test
	public void testRetrievePlayer() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			// create account, league, and manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			AccountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			league.setLeagueID(TEST_ID1);
			league.setLeagueName(TEST_NAME1);
			LeagueDAO.create(league);
			
			Manager manager = new Manager();
			manager.setEmail(TEST_ACCOUNT_EMAIL1);
			manager.setLeague_id(TEST_ID1);
			ManagerDAO.create(manager);
			
			int managerID = ManagerDAO.retrieveAnAccountsManagers
					(TEST_ACCOUNT_EMAIL1).get(0).getManagerID();
			
			RosterDAO.addPlayer(managerID, 1);
			
			Player p = RosterDAO.retrievePlayer(managerID, 1);
			
			assertTrue(p.getPlayerID() == 1);
		}
		catch (LeagueDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RosterDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Roster "
						+ "WHERE Manager_ID=? AND Player_ID=?");
				ps.setInt(1, ManagerDAO.retrieveAnAccountsManagers
						(TEST_ACCOUNT_EMAIL1).get(0).getManagerID());
				ps.setInt(2, 1);
				ps.executeUpdate();
				
				ManagerDAO.delete(ManagerDAO.retrieveAnAccountsManagers
				(TEST_ACCOUNT_EMAIL1).get(0));
				
				LeagueDAO.delete(LeagueDAO.retrieve(TEST_ID1));
				
				AccountDAO.delete(TEST_ACCOUNT_EMAIL1);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ManagerDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}
}
