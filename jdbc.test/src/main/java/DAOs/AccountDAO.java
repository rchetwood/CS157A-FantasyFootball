package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import Models.Account;

public class AccountDAO {
	
	private final String CREATE_ACCOUNT = "INSERT INTO Account (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
	private final String RETRIEVE_ALL_ACCOUNTS = "SELECT * FROM Account";
	
	public void create(Account account) {
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
			System.err.println("Unable to create Account.");
			e.printStackTrace();
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

	public List<Account> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Account retrieve(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Account obj) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Account obj) {
		// TODO Auto-generated method stub
		
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
