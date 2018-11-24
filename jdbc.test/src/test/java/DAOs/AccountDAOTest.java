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
import Models.Account;

public class AccountDAOTest {

	private final String TEST_FIRST_NAME1 = "John";
	private final String TEST_LAST_NAME1 = "Doe";
	private final String TEST_EMAIL1 = "john.doe@gmail.com";
	private final String TEST_PASSWORD1 = "1234";

	private final String TEST_FIRST_NAME2 = "Riley";
	private final String TEST_LAST_NAME2 = "Chetwood";
	private final String TEST_EMAIL2 = "rileychetwood@gmail.com";
	private final String TEST_PASSWORD2 = "5678";

	private final String TEST_FIRST_NAME3 = "Luz";
	private final String TEST_LAST_NAME3 = "Hernandez";
	private final String TEST_EMAIL3 = "luz.p.hernandez@gmail.com";
	private final String TEST_PASSWORD3 = "9101";

	private AccountDAO accountDAO;

	@Before
	public void setUp() {
		accountDAO = new AccountDAO();
	}

	@After
	public void tearDown() {
		accountDAO = null;
		assertNull(accountDAO);
	}

	@Test
	public void testCreate() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Account acc = new Account();
			Account resultAcc = new Account();
			acc.setFirstname(TEST_FIRST_NAME1);
			acc.setLastname(TEST_LAST_NAME1);
			acc.setEmail(TEST_EMAIL1);
			acc.setPassword(TEST_PASSWORD1);

			accountDAO.create(acc);
			PreparedStatement ps = conn.prepareStatement("SELECT firstName, lastName, email, password "
					+ "FROM Account "
					+ "WHERE email=?");
			ps.setString(1, TEST_EMAIL1);
			ResultSet rs = ps.executeQuery();			
			if(rs.next()) {
				resultAcc = AccountDAO.extractUser(rs);
			}
			ps.close();
			assertTrue(resultAcc.getEmail().equals(acc.getEmail()));
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			System.err.println("That email still is already in use");
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");
				ps.setString(1, TEST_EMAIL1);
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
		try {
			Account acc1 = new Account();
			acc1.setFirstname(TEST_FIRST_NAME1);
			acc1.setLastname(TEST_LAST_NAME1);
			acc1.setEmail(TEST_EMAIL1);
			acc1.setPassword(TEST_PASSWORD1);

			Account acc2 = new Account();
			acc2.setFirstname(TEST_FIRST_NAME2);
			acc2.setLastname(TEST_LAST_NAME2);
			acc2.setEmail(TEST_EMAIL2);
			acc2.setPassword(TEST_PASSWORD2);

			Account acc3 = new Account();
			acc3.setFirstname(TEST_FIRST_NAME3);
			acc3.setLastname(TEST_LAST_NAME3);
			acc3.setEmail(TEST_EMAIL3);
			acc3.setPassword(TEST_PASSWORD3);

			accountDAO.create(acc1);
			accountDAO.create(acc2);
			accountDAO.create(acc3);

			ArrayList<Account> allAccounts = (ArrayList<Account>) accountDAO.retrieveAll();

			assertTrue(allAccounts.contains(acc1));
			assertTrue(allAccounts.contains(acc2));
			assertTrue(allAccounts.contains(acc3));
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			Connection conn = ConnectionFactory.getConnections();
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");


				ps.setString(1, TEST_EMAIL1);
				ps.executeUpdate();


				ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");


				ps.setString(1, TEST_EMAIL2);
				ps.executeUpdate();

				ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");


				ps.setString(1, TEST_EMAIL3);
				ps.executeUpdate();
				ps.close();
			} 
			catch (SQLException e) {
				System.err.println("Unable to delete test entries in retrieve all test.");
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
	public void testRetrieveExistingAccount() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Account acc = new Account();
			Account resultAcc;
			acc.setFirstname(TEST_FIRST_NAME1);
			acc.setLastname(TEST_LAST_NAME1);
			acc.setEmail(TEST_EMAIL1);
			acc.setPassword(TEST_PASSWORD1);

			accountDAO.create(acc);
			resultAcc = accountDAO.retrieve(TEST_EMAIL1);

			assertTrue(resultAcc.equals(acc));
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");
				ps.setString(1, TEST_EMAIL1);
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
	public void testRetrieveNonExistingAccount() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Account acc = new Account();
			Account resultAcc;
			acc.setFirstname(TEST_FIRST_NAME1);
			acc.setLastname(TEST_LAST_NAME1);
			acc.setEmail(TEST_EMAIL1);
			acc.setPassword(TEST_PASSWORD1);

			resultAcc = accountDAO.retrieve(TEST_EMAIL1);

			assertTrue(resultAcc == null);
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");
				ps.setString(1, TEST_EMAIL1);
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
	public void testUpdate() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Account acc = new Account();
			Account resultAcc;
			acc.setFirstname(TEST_FIRST_NAME1);
			acc.setLastname(TEST_LAST_NAME1);
			acc.setEmail(TEST_EMAIL1);
			acc.setPassword(TEST_PASSWORD1);

			accountDAO.create(acc);
			
			acc.setFirstname("test first name");
			acc.setLastname("test last name");
			acc.setPassword("test password");
			
			accountDAO.update(acc);
			
			resultAcc = accountDAO.retrieve(acc.getEmail());
			
			assertTrue(resultAcc.getFirstname().equals("test first name"));
			assertTrue(resultAcc.getLastname().equals("test last name"));
			assertTrue(resultAcc.getPassword().equals("test password"));
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");
				ps.setString(1, TEST_EMAIL1);
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
	public void testDeleteNonExistingAccount() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Account acc = new Account();
			Account resultAcc;
			acc.setFirstname(TEST_FIRST_NAME1);
			acc.setLastname(TEST_LAST_NAME1);
			acc.setEmail(TEST_EMAIL1);
			acc.setPassword(TEST_PASSWORD1);
			
			accountDAO.delete(acc.getEmail());
			
			resultAcc = accountDAO.retrieve(acc.getEmail());
			
			assertTrue(resultAcc == null);
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");
				ps.setString(1, TEST_EMAIL1);
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
	public void testDeleteExistingAccount() {
		Connection conn = ConnectionFactory.getConnections();
		try {
			Account acc = new Account();
			Account resultAcc;
			acc.setFirstname(TEST_FIRST_NAME1);
			acc.setLastname(TEST_LAST_NAME1);
			acc.setEmail(TEST_EMAIL1);
			acc.setPassword(TEST_PASSWORD1);

			accountDAO.create(acc);
			
			accountDAO.delete(acc.getEmail());
			
			resultAcc = accountDAO.retrieve(acc.getEmail());
			
			assertTrue(resultAcc == null);
		}
		catch(AccountDAOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE "
						+ "FROM Account "
						+ "WHERE email=?");
				ps.setString(1, TEST_EMAIL1);
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
