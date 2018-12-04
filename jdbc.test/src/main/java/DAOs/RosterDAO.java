package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.ManagerDAOException;
import Exceptions.RosterDAOException;
import Models.Player;
import Models.Roster;

public class RosterDAO {

	private final static String ADD_PLAYER = "INSERT INTO Roster (Manager_ID, Player_ID) values "
			+ "(? , ?)";
	private final static String DELETE_PLAYER = "DELETE FROM Roster "
			+ "WHERE Manager_ID=? AND Player_ID=?";
	private final static String GET_ROSTER = "SELECT * FROM Player "
			+ "WHERE Player_ID IN (SELECT Player_ID FROM Roster WHERE Manager_ID=?)";
	private final static String AVAILABLE_PLAYERS = "SELECT * "
												  + "FROM  Player "
												  + "WHERE Player_ID NOT IN "
												  	+ "("
												  		+ "SELECT Player_ID "
												  		+ "FROM "
												  			+ "("
												  				+ "SELECT Manager_ID FROM Manager "
												  				+ "WHERE League_ID=?"
												  			+ ") AS T"
												  	+ " NATURAL JOIN Roster"
												  	+ ")";
	
	public static List<Player> availablePlayers(int managerID) throws RosterDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<Player> availablePlayers = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(AVAILABLE_PLAYERS);
			int leagueID = ManagerDAO.retrieveAManager(managerID).getLeague_id();
			ps.setInt(1, leagueID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Player p = PlayerDAO.extractPlayer(rs);
				availablePlayers.add(p);
			}
			return availablePlayers;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RosterDAOException("Unable to see if player is taken.");
		} catch (ManagerDAOException e) {
			throw new RosterDAOException("Unable to get league ID to check available players.");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}

	private static boolean isTaken(int managerID, int playerID) throws RosterDAOException  {
		ArrayList<Player> availablePlayers = (ArrayList<Player>) RosterDAO.availablePlayers(managerID);
		Player findThisPlayer = new Player();
		findThisPlayer.setPlayerID(playerID);
		for(Player p : availablePlayers) {
			if(p.getPlayerID() == findThisPlayer.getPlayerID()) {
				return false;
			}
		}
		return true;
	}
	
	public static Player retrievePlayer(int managerID, int playerID) throws RosterDAOException {
		Connection conn = ConnectionFactory.getConnections();
		Player p = new Player();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT Player_ID FROM Roster WHERE Manager_ID=? AND Player_ID=?");
			ps.setInt(1, managerID);
			ps.setInt(2, playerID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int playerIDTemp = rs.getInt("Player_ID");
				ps = conn.prepareStatement("SELECT * FROM Player WHERE Player_ID=?");
				ps.setInt(1, playerID);
				rs = ps.executeQuery();
				if(rs.next())
					p = PlayerDAO.extractPlayer(rs);
				return p;
			}
			else {
				throw new RosterDAOException("Player is not on roster.");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RosterDAOException("Unable to get player from roster");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}
	
	public static void addPlayer(int managerID, int playerID) throws RosterDAOException {
		Connection conn = ConnectionFactory.getConnections();
		try {
			if(!isTaken(managerID, playerID)) {
				PreparedStatement ps = conn.prepareStatement(ADD_PLAYER);
				ps.setInt(1, managerID);
				ps.setInt(2, playerID);
				ps.executeUpdate();
				ps.close();
			}
			else {
				throw new RosterDAOException("Player is taken.");
			}
		}
		catch(SQLException e) {
			throw new RosterDAOException("Unable to add player.");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}

	public static void deletePlayer(int managerID, int playerID) throws RosterDAOException {
		Connection conn = ConnectionFactory.getConnections();
		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_PLAYER);
			ps.setInt(1, managerID);
			ps.setInt(2, playerID);
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e) {
			throw new RosterDAOException("Unable to delete player.");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}

	public static List<Player> retrieveManagersRoster(int managerID) throws RosterDAOException {
		Connection conn = ConnectionFactory.getConnections();
		List<Player> roster = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(GET_ROSTER);
			ps.setInt(1, managerID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Player p = PlayerDAO.extractPlayer(rs);
				roster.add(p);
			}
			ps.close();
			return roster;
		}
		catch(SQLException e) {
			throw new RosterDAOException("Unable to get roster.");
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				System.err.println("Unable to close connection.");
			}
		}
	}
	
	public static Roster extractRoster(ResultSet rs) throws SQLException {
		Roster r = new Roster();
		r.setManagerID(rs.getInt("Manager_ID"));
		r.setPlayerID(rs.getInt("Player_ID"));
		return r;
	}
}
