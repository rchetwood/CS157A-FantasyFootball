package DAOs;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Exceptions.AccountDAOException;
import Exceptions.LeagueDAOException;
import Exceptions.ManagerDAOException;
import Models.Account;
import Models.League;
import Models.Manager;

public class ManagerDAOTest {

	private final String TEST_ACCOUNT_EMAIL1 = "TEST";
	private final String TEST_FIRST_NAME1 = "TEST";
	private final String TEST_LAST_NAME1 = "TEST";
	private final String TEST_PASSWORD1 = "TEST";

	private final String TEST_ACCOUNT_EMAIL2 = "TESTTEST";
	private final String TEST_FIRST_NAME2 = "TESTTEST";
	private final String TEST_LAST_NAME2 = "TESTTEST";
	private final String TEST_PASSWORD2 = "TESTTEST";

	private final String TEST_ACCOUNT_EMAIL3 = "TESTTESTTEST";
	private final String TEST_FIRST_NAME3 = "TESTTESTTEST";
	private final String TEST_LAST_NAME3 = "TESTTESTTEST";
	private final String TEST_PASSWORD3 = "TESTTESTTEST";

	private final int TEST_NUMBER_OF_TEAMS1 = 42;
	private final java.sql.Date TEST_DRAFT_DATE1 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);

	private final int TEST_NUMBER_OF_TEAMS2 = 314;
	private final java.sql.Date TEST_DRAFT_DATE2 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);

	private final int TEST_NUMBER_OF_TEAMS3 = 217;
	private final java.sql.Date TEST_DRAFT_DATE3 = 
			new java.sql.Date(System.currentTimeMillis() - 25200000);

	private final String RETRIEVE_TEST_MANAGER = "SELECT * FROM Manager "
			+ "WHERE email=? AND League_ID=?";
	private final String DELETE_TEST_MANAGER = "DELETE FROM Manager "
			+ "WHERE Manager_ID=?";


	private ManagerDAO managerDAO;
	private AccountDAO accountDAO;
	private LeagueDAO leagueDAO;

	@Before
	public void setup() {
		managerDAO = new ManagerDAO();
		accountDAO = new AccountDAO();
		leagueDAO = new LeagueDAO();
	}

	@After
	public void tearDown() {
		managerDAO = null;
		accountDAO = null;
		leagueDAO = null;
		assertNull(managerDAO);
		assertNull(accountDAO);
		assertNull(leagueDAO);
	}

	@Test
	public void testCreate() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Manager manager = new Manager();
			Manager resultManager = new Manager();

			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);

			accountDAO.create(account);
			leagueDAO.create(league);

			//retrieve the db entities
			account = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				league = LeagueDAO.extractLeague(rs);
			}

			manager.setEmail(account.getEmail());
			manager.setLeague_id(league.getLeagueID());

			managerDAO.create(manager);

			ps = conn.prepareStatement(RETRIEVE_TEST_MANAGER);
			ps.setString(1, account.getEmail());
			ps.setInt(2, league.getLeagueID());
			rs = ps.executeQuery();
			if(rs.next())
				resultManager = ManagerDAO.extractManager(rs);

			assert(resultManager != null);
			assertTrue(resultManager.getEmail().equals(manager.getEmail()));
			assertTrue(resultManager.getLeague_id() == manager.getLeague_id());
		}
		catch (ManagerDAOException e) {
			e.printStackTrace();
		} catch (AccountDAOException e) {

			e.printStackTrace();
		} catch (LeagueDAOException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} 

		finally {
			try {
				//retrieve the db entities
				League league = new League();
				Account account = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					league = LeagueDAO.extractLeague(rs);
				}

				Manager manager = null;
				ps = conn.prepareStatement(RETRIEVE_TEST_MANAGER);
				ps.setString(1, account.getEmail());
				ps.setInt(2, league.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager = ManagerDAO.extractManager(rs);

				// delete entities
				ps = conn.prepareStatement(DELETE_TEST_MANAGER);
				ps.setInt(1, manager.getManagerID());
				ps.executeUpdate();

				leagueDAO.delete(league);
				accountDAO.delete(manager.getEmail());

				ps.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			} catch (LeagueDAOException e) {

				e.printStackTrace();
			} catch (AccountDAOException e) {

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
	public void testRetrieveAnAccountsManagers() {
		Connection conn = ConnectionFactory.getConnections();

		try {

			// create single account and multiple leagues and managers
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);

			Manager manager1 = new Manager();
			Manager manager2 = new Manager();
			Manager manager3 = new Manager();

			League league1 = new League();
			League league2 = new League();
			League league3 = new League();

			// set values of leagues and managers
			league1.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league1.setDraft_date(TEST_DRAFT_DATE1);

			league2.setNumber_of_teams(TEST_NUMBER_OF_TEAMS2);
			league2.setDraft_date(TEST_DRAFT_DATE2);

			league3.setNumber_of_teams(TEST_NUMBER_OF_TEAMS3);
			league3.setDraft_date(TEST_DRAFT_DATE3);

			leagueDAO.create(league1);
			leagueDAO.create(league2);
			leagueDAO.create(league3);

			accountDAO.create(account);

			// retrieve leagues and account to get ids so
			// that we can create managers
			// league 1
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				league1 = LeagueDAO.extractLeague(rs);
			}

			// league 2
			ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS2);
			ps.setDate(2, TEST_DRAFT_DATE2);

			rs = ps.executeQuery();
			if(rs.next()) {
				league2 = LeagueDAO.extractLeague(rs);
			}

			// league 3
			ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS3);
			ps.setDate(2, TEST_DRAFT_DATE3);

			rs = ps.executeQuery();
			if(rs.next()) {
				league3 = LeagueDAO.extractLeague(rs);
			}

			// account
			account = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);

			// create managers
			manager1.setEmail(account.getEmail());
			manager1.setLeague_id(league1.getLeagueID());

			manager2.setEmail(account.getEmail());
			manager2.setLeague_id(league2.getLeagueID());

			manager3.setEmail(account.getEmail());
			manager3.setLeague_id(league3.getLeagueID());

			// insert into db
			managerDAO.create(manager1);
			managerDAO.create(manager2);
			managerDAO.create(manager3);

			// get managers associated with given account
			List<Manager> allManagersFromGivenAccount = 
					managerDAO.retrieveAnAccountsManagers(account.getEmail());

			// assert the values are all there
			assertTrue(allManagersFromGivenAccount.size() == 3);
		}
		catch (LeagueDAOException e) {

			e.printStackTrace();
		} catch (AccountDAOException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ManagerDAOException e) {

			e.printStackTrace();
		}
		finally {
			// delete test entities
			try {
				Account account = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);
				League league1 = new League();
				League league2 = new League();
				League league3 = new League();
				Manager manager1 = new Manager();
				Manager manager2 = new Manager();
				Manager manager3 = new Manager();

				// league 1
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					league1 = LeagueDAO.extractLeague(rs);
				}

				// league 2
				ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS2);
				ps.setDate(2, TEST_DRAFT_DATE2);

				rs = ps.executeQuery();
				if(rs.next()) {
					league2 = LeagueDAO.extractLeague(rs);
				}

				// league 3
				ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS3);
				ps.setDate(2, TEST_DRAFT_DATE3);

				rs = ps.executeQuery();
				if(rs.next()) {
					league3 = LeagueDAO.extractLeague(rs);
				}

				// retrieve managers
				// manager 1
				ps = conn.prepareStatement(RETRIEVE_TEST_MANAGER);
				ps.setString(1, account.getEmail());
				ps.setInt(2, league1.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager1 = ManagerDAO.extractManager(rs);

				// manager 2
				ps = conn.prepareStatement(RETRIEVE_TEST_MANAGER);
				ps.setString(1, account.getEmail());
				ps.setInt(2, league2.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager2 = ManagerDAO.extractManager(rs);

				// manager 3
				ps = conn.prepareStatement(RETRIEVE_TEST_MANAGER);
				ps.setString(1, account.getEmail());
				ps.setInt(2, league3.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager3 = ManagerDAO.extractManager(rs);

				// delete entities
				ps = conn.prepareStatement(DELETE_TEST_MANAGER);
				ps.setInt(1, manager1.getManagerID());
				ps.executeUpdate();

				ps = conn.prepareStatement(DELETE_TEST_MANAGER);
				ps.setInt(1, manager2.getManagerID());
				ps.executeUpdate();

				ps = conn.prepareStatement(DELETE_TEST_MANAGER);
				ps.setInt(1, manager3.getManagerID());
				ps.executeUpdate();

				leagueDAO.delete(league1);
				leagueDAO.delete(league2);
				leagueDAO.delete(league3);
				accountDAO.delete(manager1.getEmail());


				ps.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			} catch (LeagueDAOException e) {

				e.printStackTrace();
			} catch (AccountDAOException e) {

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
	public void testRetrieveAManager() {
		Connection conn = ConnectionFactory.getConnections();

		try {
			// create test account and league for manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			accountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			leagueDAO.create(league);

			// retrieve league for key
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				league = LeagueDAO.extractLeague(rs);
			}

			// create manager
			Manager manager = new Manager();
			manager.setEmail(account.getEmail());
			manager.setLeague_id(league.getLeagueID());
			managerDAO.create(manager);

			// retrieve manager to get key for verifying
			ps = conn.prepareStatement("SELECT * FROM Manager "
					+ "WHERE email=? AND League_ID=?");
			ps.setString(1, account.getEmail());
			ps.setInt(2, league.getLeagueID());
			rs = ps.executeQuery();
			if(rs.next())
				manager = ManagerDAO.extractManager(rs);

			// test retrieve manager function
			Manager resultManager = managerDAO.retrieveAManager(manager.getManagerID());

			assertTrue(resultManager.equals(manager));
		}
		catch(ManagerDAOException e) {
			e.printStackTrace();
		} catch (AccountDAOException e) {

			e.printStackTrace();
		} catch (LeagueDAOException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		finally {
			try {
				Account account = null;
				League league = null;
				Manager manager = null;

				// retrieve account
				account = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);

				// retrieve league 
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					league = LeagueDAO.extractLeague(rs);
				}

				// retrieve manager
				ps = conn.prepareStatement("SELECT * FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, account.getEmail());
				ps.setInt(2, league.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager = ManagerDAO.extractManager(rs);

				// delete manager
				ps = conn.prepareStatement("DELETE FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, account.getEmail());
				ps.setInt(2, league.getLeagueID());
				ps.executeUpdate();

				leagueDAO.delete(league);
				accountDAO.delete(account.getEmail());
			}
			catch(SQLException e) {
				e.printStackTrace();
			} catch (LeagueDAOException e) {

				e.printStackTrace();
			} catch (AccountDAOException e) {

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
	public void testRetrieveAllManagerInLeague() {
		Connection conn = ConnectionFactory.getConnections();

		try {
			// create a single league
			League league = new League();

			// create 3 accounts and 3 managers
			Account account1 = new Account();
			Account account2 = new Account();
			Account account3 = new Account();

			Manager manager1 = new Manager();
			Manager manager2 = new Manager();
			Manager manager3 = new Manager();

			// add values for league accounts and managers
			league.setDraft_date(TEST_DRAFT_DATE1);
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);

			account1.setEmail(TEST_ACCOUNT_EMAIL1);
			account1.setFirstname(TEST_FIRST_NAME1);
			account1.setLastname(TEST_LAST_NAME1);
			account1.setPassword(TEST_PASSWORD1);

			account2.setEmail(TEST_ACCOUNT_EMAIL2);
			account2.setFirstname(TEST_FIRST_NAME2);
			account2.setLastname(TEST_LAST_NAME2);
			account2.setPassword(TEST_PASSWORD2);

			account3.setEmail(TEST_ACCOUNT_EMAIL3);
			account3.setFirstname(TEST_FIRST_NAME3);
			account3.setLastname(TEST_LAST_NAME3);
			account3.setPassword(TEST_PASSWORD3);

			// create league and accounts in db
			leagueDAO.create(league);

			accountDAO.create(account1);
			accountDAO.create(account2);
			accountDAO.create(account3);

			// get league from db for key
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, league.getNumber_of_teams());
			ps.setDate(2, league.getDraft_date());

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				league = LeagueDAO.extractLeague(rs);
			}

			// get accounts
			account1 = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);
			account2 = accountDAO.retrieve(TEST_ACCOUNT_EMAIL2);
			account3 = accountDAO.retrieve(TEST_ACCOUNT_EMAIL3);

			// add values to managers
			manager1.setEmail(account1.getEmail());
			manager1.setLeague_id(league.getLeagueID());

			manager2.setEmail(account2.getEmail());
			manager2.setLeague_id(league.getLeagueID());

			manager3.setEmail(account3.getEmail());
			manager3.setLeague_id(league.getLeagueID());

			// add managers to db
			managerDAO.create(manager1);
			managerDAO.create(manager2);
			managerDAO.create(manager3);

			// test get all manager from given league function
			List<Manager> allManagersInGivenLeague = 
					managerDAO.retrieveAllManagerInLeague(league.getLeagueID());

			assertTrue(allManagersInGivenLeague.size() == 3);

		} catch (AccountDAOException e) {

			e.printStackTrace();
		} catch (LeagueDAOException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ManagerDAOException e) {

			e.printStackTrace();
		}
		finally {
			try {
				// get accounts 
				Account account1 = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);
				Account account2 = accountDAO.retrieve(TEST_ACCOUNT_EMAIL2);
				Account account3 = accountDAO.retrieve(TEST_ACCOUNT_EMAIL3);

				// get league 
				League league = null;
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					league = LeagueDAO.extractLeague(rs);
				}


				// get managers
				Manager manager1 = null;
				Manager manager2 = null;
				Manager manager3 = null;

				// manager 1
				ps = conn.prepareStatement("SELECT * FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, account1.getEmail());
				ps.setInt(2, league.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager1 = ManagerDAO.extractManager(rs);

				// manager 2
				ps = conn.prepareStatement("SELECT * FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, account2.getEmail());
				ps.setInt(2, league.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager2 = ManagerDAO.extractManager(rs);

				// manager 3
				ps = conn.prepareStatement("SELECT * FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, account3.getEmail());
				ps.setInt(2, league.getLeagueID());
				rs = ps.executeQuery();
				if(rs.next())
					manager3 = ManagerDAO.extractManager(rs);

				// delete managers
				//manager 1
				ps = conn.prepareStatement("DELETE FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, manager1.getEmail());
				ps.setInt(2, manager1.getLeague_id());
				ps.executeUpdate();

				//manager 2
				ps = conn.prepareStatement("DELETE FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, manager2.getEmail());
				ps.setInt(2, manager2.getLeague_id());
				ps.executeUpdate();


				//manager 3
				ps = conn.prepareStatement("DELETE FROM Manager "
						+ "WHERE email=? AND League_ID=?");
				ps.setString(1, manager3.getEmail());
				ps.setInt(2, manager3.getLeague_id());
				ps.executeUpdate();

				// delete league and accounts
				leagueDAO.delete(league);
				accountDAO.delete(account1.getEmail());
				accountDAO.delete(account2.getEmail());
				accountDAO.delete(account3.getEmail());

				//done
				ps.close();
			} catch (AccountDAOException e) {

				e.printStackTrace();
			} catch (LeagueDAOException e) {

				e.printStackTrace();
			} catch (SQLException e) {

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
	public void testDelete() {
		Connection conn = ConnectionFactory.getConnections();

		try {
			// create test account and league for manager
			Account account = new Account();
			account.setEmail(TEST_ACCOUNT_EMAIL1);
			account.setFirstname(TEST_FIRST_NAME1);
			account.setLastname(TEST_LAST_NAME1);
			account.setPassword(TEST_PASSWORD1);
			accountDAO.create(account);

			League league = new League();
			league.setNumber_of_teams(TEST_NUMBER_OF_TEAMS1);
			league.setDraft_date(TEST_DRAFT_DATE1);
			leagueDAO.create(league);

			// retrieve league for key
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
					+ "WHERE Number_of_Teams=? AND Draft_Date=?");
			ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
			ps.setDate(2, TEST_DRAFT_DATE1);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				league = LeagueDAO.extractLeague(rs);
			}

			// create manager
			Manager manager = new Manager();
			manager.setEmail(account.getEmail());
			manager.setLeague_id(league.getLeagueID());
			managerDAO.create(manager);

			// retrieve manager to get key for verifying
			ps = conn.prepareStatement("SELECT * FROM Manager "
					+ "WHERE email=? AND League_ID=?");
			ps.setString(1, account.getEmail());
			ps.setInt(2, league.getLeagueID());
			rs = ps.executeQuery();
			if(rs.next())
				manager = ManagerDAO.extractManager(rs);

			// test delete
			managerDAO.delete(manager);

			// should be null
			try {
				Manager resultManager = managerDAO.retrieveAManager(manager.getManagerID());
			} catch(ManagerDAOException e) {
				if(!e.getMessage().equals("Manager does not exist.")) {
					fail();
				}
			}
		}
		catch (AccountDAOException e) {
			e.printStackTrace();
		} catch (LeagueDAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ManagerDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				Account account = null;
				League league = null;

				// retrieve account
				account = accountDAO.retrieve(TEST_ACCOUNT_EMAIL1);

				// retrieve league 
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM League "
						+ "WHERE Number_of_Teams=? AND Draft_Date=?");
				ps.setInt(1, TEST_NUMBER_OF_TEAMS1);
				ps.setDate(2, TEST_DRAFT_DATE1);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					league = LeagueDAO.extractLeague(rs);
				}
				
				leagueDAO.delete(league);
				accountDAO.delete(account.getEmail());
			}
			catch(SQLException e) {
				e.printStackTrace();
			} catch (LeagueDAOException e) {
				e.printStackTrace();
			} catch (AccountDAOException e) {
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
