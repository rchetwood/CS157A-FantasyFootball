package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.ManagerDAOException;
import Models.League;
import Models.Manager;

public class ManagerDAO {

	private static final String CREATE_MANAGER = "INSERT INTO Manager (email, League_ID) "
			+ "VALUES(?, ?)";
	private static final String RETRIEVE_ACCOUNTS_MANAGERS = "SELECT * "
			+ "FROM Manager WHERE email=?";
	private static final String RETRIEVE_MANAGER = "SELECT * FROM Manager "
			+ "WHERE Manager_ID=?";
	private static final String RETIEVE_MANAGERS_IN_LEAGUE = "SELECT * FROM Manager "
			+ "WHERE League_ID=?";
	private static final String DELETE_MANAGER = "DELETE FROM Manager "
			+ "WHERE Manager_ID=?";
	private static final String RETRIEVE_LEAGUES_ON_ACCOUNT = "SELECT * FROM League "
			+ "WHERE League_ID IN (SELECT League_ID FROM Manager "
			+ "WHERE email=?)";
	private static final String RETRIEVE_MANAGER_FOR_LEAGUE = "SELECT * FROM Manager "
			+ "WHERE League_ID = ? && email = ?";

	public static void create(Manager manager) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();

		try {
			PreparedStatement ps = conn.prepareStatement(CREATE_MANAGER);
			ps.setString(1, manager.getEmail());
			ps.setInt(2, manager.getLeague_id());
			ps.executeUpdate();
			ps.close();
		} 
		catch(SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to create manager.");
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
	
	public static Manager retrieveManagerForLeague(String email, int leagueID) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();
		Manager manager = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_MANAGER_FOR_LEAGUE);
			ps.setInt(1, leagueID);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				manager = ManagerDAO.extractManager(rs);
			} else {
				throw new ManagerDAOException("Manager does not exist");
			}
			ps.close();
			return manager;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to get manager for this account");
		}
		finally {
			try {
				conn.close();
			}
			catch (SQLException e) {
				System.err.println("Unable to close connection");
				e.printStackTrace();
			}
		}
	}
	
	public static List<League> retrieveAllLeaguesFromAccount(String email) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<League> allLeagueFromAccount = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_LEAGUES_ON_ACCOUNT);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				League league = LeagueDAO.extractLeague(rs);
				allLeagueFromAccount.add(league);
			}
			ps.close();
			return allLeagueFromAccount;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to get all league associated with that account.");
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

	public static List<Manager> retrieveAnAccountsManagers(String email) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<Manager> allManagerOnAnAccount = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_ACCOUNTS_MANAGERS);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Manager manager = ManagerDAO.extractManager(rs);
				allManagerOnAnAccount.add(manager);
			}
			ps.close();
			return allManagerOnAnAccount;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to retrieve the account's managers.");
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

	public static Manager retrieveAManager(int managerID) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();
		Manager manager = new Manager();

		try {
			PreparedStatement ps = conn.prepareStatement(RETRIEVE_MANAGER);
			ps.setInt(1, managerID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				manager = ManagerDAO.extractManager(rs);
			}
			else {
				throw new ManagerDAOException("Manager does not exist.");
			}
			ps.close();
			return manager;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to retrieve the managers.");
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

	public static List<Manager> retrieveAllManagerInLeague(int leagueID) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<Manager> allManagersInGivenLeague = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement(RETIEVE_MANAGERS_IN_LEAGUE);
			ps.setInt(1, leagueID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Manager manager = ManagerDAO.extractManager(rs);
				allManagersInGivenLeague.add(manager);
			}
			ps.close();
			return allManagersInGivenLeague;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to retrieve managers in the league");
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

	public static void delete(Manager manager) throws ManagerDAOException {
		Connection conn = ConnectionFactory.getConnections();
		
		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_MANAGER);
			ps.setInt(1, manager.getManagerID());
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new ManagerDAOException("Unable to delete manager");
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

	public static Manager extractManager(ResultSet rs) throws SQLException {
		Manager manager = new Manager();

		manager.setManagerID(rs.getInt("Manager_ID"));
		manager.setEmail(rs.getString("email"));
		manager.setLeague_id(rs.getInt("League_ID"));

		return manager;
	}

}
