package DAOs;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Models.Account;

public class AccountDAOTest {
	
	private final String TEST_FIRST_NAME = "John";
	private final String TEST_LAST_NAME = "Doe";
	private final String TEST_EMAIL = "john.doe@gmail.com";
	private final String TEST_PASSWORD = "1234";
	
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
			acc.setFirstname(TEST_FIRST_NAME);
			acc.setLastname(TEST_LAST_NAME);
			acc.setEmail(TEST_EMAIL);
			acc.setPassword(TEST_PASSWORD);
			
			accountDAO.create(acc);
			
			PreparedStatement ps = conn.prepareStatement("SELECT firstName, lastName, email, password "
														+ "FROM Account "
														+ "WHERE email=?");
			
			ps.setString(1, TEST_EMAIL);
			
			System.out.println(ps.toString());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				resultAcc = AccountDAO.extractUser(rs);
			}
			
			assertTrue(resultAcc.getEmail().equals(acc.getEmail()));
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
				
				
				ps.setString(1, TEST_EMAIL);
				
				ps.executeUpdate();
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
	public void testDelete() {
		fail("Not yet implemented");
	}

}
