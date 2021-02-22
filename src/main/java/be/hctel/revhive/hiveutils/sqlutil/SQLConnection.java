package be.hctel.revhive.hiveutils.sqlutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

/**
 * 
 * @author hctel
 *
 */
public class SQLConnection {
	String user, database, host, password;
	int port;
	Connection con;
	/**
	 * 
	 * @param database : the database you want to connect to
	 * @param user : the user you want to use to connect to the database
	 * @param host : the host where the database is hosted
	 * @param port : the port on which the database server listens
	 * @param password : the password you want to use to connect to the database
	 */
	public SQLConnection(String database, String user, String host, int port, String password) {
		this.password = password;
		this.database = database;
		this.user = user;
		this.host = host;
		this.port = port;
		openConnection(this.host, this.port, this.user, this.password, this.database);
	}
	/**
	 * Get the string matching the uuid in the specified column in the specified table
	 * @param table : the table you want to look in
	 * @param uuid : the UUID of which you want to get the string
	 * @param column : the column in the table where the string is stored
	 * @return the matching string or "" if nothing is found
	 */
	public String getString(String table, String uuid, String column) {
		openConnection(host, port, user, password, database);
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE UUID = '" + uuid+ "';");
		rs.next();
		return rs.getString(column);
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * Get the int matching the uuid in the specified column in the specified table
	 * @param table : the table you want to look in
	 * @param uuid : the UUID of which you want to get the int
	 * @param column : the column in the table where the int is stored
	 * @return the matching int or 0 if nothing is found
	 */
	public int getInt(String table, String uuid, String column) {
		openConnection(host, port, user, password, database);
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE UUID = '" + uuid+ "';");
		rs.next();
		return rs.getInt(column);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * Get the JSONObject matching the uuid in the specified column in the specified table
	 * @param table : the table you want to look in
	 * @param uuid : the UUID of which you want to get the JSONObject
	 * @param column : the column in the table where the JSONObject is stored
	 * @return the matching JSONObject or null if nothing is found
	 */
	public JSONObject getJSON(String table, String uuid, String column) {
		openConnection(host, port, user, password, database);
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE UUID = '" + uuid+ "';");
		rs.next();
		return new JSONObject(rs.getString(column));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Check if a player exists in a table (player is stored with their uuid). Adds automatically the player in the table. 
	 * <b>Note: Only the UUID will be filled. All other colums will be set at default value</b>
	 * @param table : The tabble to check in
	 * @param uuid : The UUID of the player 
	 * @return if the table contained the player or not.
	 */
	public boolean checkIfPlayerExists(String table, String uuid) {
		openConnection(host, port, user, password, database);
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE UUID = '" + uuid + "';");
			if(rs.next()) return true;
			else {
				st.execute("INSERT INTO " + table + " (uuid) VALUES ('" + uuid + "');");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	public void insert(String table, String uuid, String column, int toAdd) {
		openConnection(host, port, user, password, database);
		try {
			Statement st = con.createStatement();
			st.execute("UPDATE " + table + " SET " + column + " = " + toAdd + " WHERE UUID = '" + uuid + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void insert(String table, String uuid, String column, String toAdd) {
		openConnection(host, port, user, password, database);
		try {
			Statement st = con.createStatement();
			st.execute("UPDATE " + table + " SET " + column + " = '" + toAdd + "' WHERE UUID = '" + uuid + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void openConnection(String host, int port, String user, String password, String database) {
		try {
			if (con != null && !con.isClosed()) {
			    return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	    synchronized (this) {
	        try {
				if (con != null && !con.isClosed()) {
				    return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
	        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?autoReconnect=true&useSSL=false","root","1234");
					System.out.println("SQL is operational!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}	
}