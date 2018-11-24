package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.AccountDAOException;
import Models.Account;

public class AccountDAO {

	private final String CREATE_ACCOUNT = "INSERT INTO Account (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
	private final String RETRIEVE_ALL_ACCOUNTS = "SELECT * FROM Account";
	private final String RETRIEVE_ACCOUNT = "SELECT * FROM Account WHERE email=?";
	private final String UPDATE_ACCOUNT = "UPDATE Account SET firstName=?, lastName=?, password=? WHERE email=?";
	private final String DELETE_ACCOUNT = "DELETE FROM Account WHERE email=?";

	public void create(Account account) throws AccountDAOException {
		Connection conn = ConnectionFactory.getConnections();

		try {
			PreparedStatement ps = conn.prepareStatement(CREATE_ACCOUNT);
			ps.setString(1, account.getFirstname());
			ps.setString(2, account.getLastname());
			ps.setString(3, account.getEmail());
			ps.setString(4, account.getPassword());

			ps.execute();
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AccountDAOException("Unable to create account.");
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

	public List<Account> retrieveAll() throws AccountDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<Account> allAccounts = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_ALL_ACCOUNTS);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Account acc = AccountDAO.extractUser(rs);
				allAccounts.add(acc);
			}
			ps.close();
			return allAccounts;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AccountDAOException("Unable to retrieve all Accounts.");
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

	public Account retrieve(String email) throws AccountDAOException {
		Connection conn = ConnectionFactory.getConnections();

		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_ACCOUNT);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account acc = AccountDAO.extractUser(rs);
				ps.close();
				return acc;
			}
			else {
				ps.close();
				return null;
			}

		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AccountDAOException("Unable to retrieve account.");
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

	public void update(Account account) throws AccountDAOException {
		Connection conn = ConnectionFactory.getConnections();

		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_ACCOUNT);
			ps.setString(1, account.getFirstname());
			ps.setString(2, account.getLastname());
			ps.setString(3, account.getPassword());
			ps.setString(4, account.getEmail());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AccountDAOException("Unable to update account.");
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

	public void delete(String email) throws AccountDAOException {
		Connection conn = ConnectionFactory.getConnections();

		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_ACCOUNT);
			ps.setString(1, email);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AccountDAOException("Unable to delete account.");
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

	public static Account extractUser(ResultSet rs) throws SQLException {
		Account acc = new Account();

		acc.setFirstname(rs.getString("firstName"));
		acc.setLastname(rs.getString("lastName"));
		acc.setEmail(rs.getString("email"));
		acc.setPassword(rs.getString("password"));

		return acc;
	}

}
