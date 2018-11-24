package DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	// database credentials
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_URL_PRODUCTION = "jdbc:mysql://cs157afantasyfootball2.cpfpd4dqgcvr.us-east-1.rds.amazonaws.com:3306/CS157AFF";
	private static final String DB_URL_TEST = "jdbc:mysql://cs157afantasyfootball2.cpfpd4dqgcvr.us-east-1.rds.amazonaws.com:3306/CS157AFF";
	private static final String USER = "CS157AFF";
	private static final String PASS = "CS157A010949601";
	
	public static Connection getConnections() {

		try {
			Class.forName(JDBC_DRIVER);
			return DriverManager.getConnection(DB_URL_PRODUCTION,USER,PASS);

		}
		catch(SQLException e) {
			System.out.println("Unable to connect to the production database.");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Driver class not found.");
		}
		return null;

	}
	
	public static Connection getTestDBConnections() {

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			return DriverManager.getConnection(DB_URL_TEST,USER,PASS);

		}
		catch(SQLException e) {
			System.out.println("Unable to connect to the test database.");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Driver class not found.");
		}
		return null;

	}
	
	

}
